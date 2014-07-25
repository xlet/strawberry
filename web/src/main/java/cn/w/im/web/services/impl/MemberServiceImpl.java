package cn.w.im.web.services.impl;

import cn.w.im.domains.basic.OnlineMemberStatus;
import cn.w.im.domains.basic.Status;
import cn.w.im.domains.basic.TempMember;
import cn.w.im.persistent.OnlineMemberStatusDao;
import cn.w.im.persistent.TempMemberDao;
import cn.w.im.utils.sdk.usercenter.Members;
import cn.w.im.utils.sdk.usercenter.UserCenterException;
import cn.w.im.utils.sdk.usercenter.model.MemberProfile;
import cn.w.im.web.GlobalConfiguration;
import cn.w.im.web.exceptions.IllegalMemberException;
import cn.w.im.domains.mongo.basic.MongoTempMember;
import cn.w.im.web.services.MemberService;
import cn.w.im.web.vo.LinkmanViewObject;
import cn.w.im.web.vo.MemberViewObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jackie.
 */
@Service
public class MemberServiceImpl implements MemberService {

    private static final int START_TEMP_MEMBER_ID = 121639818;

    private static final Log LOG = LogFactory.getLog(MemberServiceImpl.class);

    /**
     * member cache collection.
     * <p/>
     * key:name+referrer.
     */
    private Map<String, CacheMemberViewObject> memberCacheMap = new ConcurrentHashMap<String, CacheMemberViewObject>();

    @Autowired
    private GlobalConfiguration globalConfiguration;

    @Autowired
    private TempMemberDao tempMemberDao;

    @Autowired
    private OnlineMemberStatusDao onlineMemberStatusDao;

    @Autowired
    private Members userCenterMembers;

    @Override
    public boolean existed(String name, String referrer) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("the name is empty.");
        }

        if (this.cacheExisted(name, referrer)) {
            return true;
        }

        try {
            MemberViewObject memberViewObject = get(name, referrer);
            if (memberViewObject != null) {
                return true;
            }
        } catch (IllegalMemberException e) {
            return false;
        }
        return false;
    }


    @Override
    public LinkmanViewObject createTemp(String referrer) {
        TempMember tempMember = createAndSave(referrer);
        LinkmanViewObject linkmanViewObject = new LinkmanViewObject();
        linkmanViewObject.setName(tempMember.getNickname());
        linkmanViewObject.setId(tempMember.getName());
        linkmanViewObject.setStatus(Status.Online.getValue());

        if (!this.cacheExisted(tempMember.getName(), referrer)) {
            this.cacheAdd(tempMember, referrer);
        }
        return linkmanViewObject;
    }

    @Override
    public MemberViewObject get(String memberId, String referrer) {
        if (StringUtils.isEmpty(memberId)) {
            return new MemberViewObject();
        }

        if (this.cacheExisted(memberId, referrer)) {
            MemberViewObject memberViewObject = this.getCachedMember(memberId, referrer);
            LOG.debug("cached member:" + memberId + referrer + " status value:" + memberViewObject.getStatus());
            return memberViewObject;
        }

        MemberViewObject memberViewObject = null;

        TempMember tempMember = tempMemberDao.getByName(memberId);
        if (tempMember != null) {
            memberViewObject = createByTempMember(tempMember, referrer);
        }

        try {
            MemberProfile memberProfile = userCenterMembers.getByWid(memberId);
            memberViewObject = createByUserCenter(memberProfile, referrer);
        } catch (UserCenterException e) {
            LOG.error(e);
        }

        if (memberViewObject != null) {
            if (!this.cacheExisted(memberViewObject.getId(), referrer)) {
                this.cacheAdd(memberViewObject, referrer);
            }
            return memberViewObject;
        }

        throw new IllegalMemberException(memberId);
    }

    @Override
    public void online(String loginId, String referrer) {
        MemberViewObject memberViewObject = this.get(loginId, referrer);
        memberViewObject.setStatus(Status.Online.getValue());
        LOG.debug("set member:" + loginId + referrer + " online. status value:" + memberViewObject.getStatus());
    }

    @Override
    public Status getStatus(String memberId, String referrer) {
        if (this.cacheExisted(memberId, referrer)) {
            return Status.valueOf(this.getCachedMember(memberId, referrer).getStatus());
        }

        OnlineMemberStatus memberStatus = this.onlineMemberStatusDao.get(memberId);
        return memberStatus.getStatus();
    }

    private MemberViewObject createByUserCenter(MemberProfile memberProfile, String referrer) {
        MemberViewObject memberViewObject = new MemberViewObject();
        memberViewObject.setThumb(memberProfile.getPhotoUrl());
        if (StringUtils.isEmpty(memberProfile.getPhotoUrl())) {
            memberViewObject.setThumb(globalConfiguration.getDefaultThumb());
        }
        memberViewObject.setNickName(memberProfile.getNickname());
        memberViewObject.setId(memberProfile.getWid());
        memberViewObject.setMerchant(memberProfile.isMerchant());
        memberViewObject.setAddress(memberProfile.getAddress());
        memberViewObject.setEmail(memberProfile.getEmail());
        memberViewObject.setMobile(memberProfile.getMobile());
        memberViewObject.setMobileValid(memberProfile.isMobileValid());
        memberViewObject.setRealNameValid(memberProfile.isRealNameValid());
        //todo: jackie get merchant info.
        memberViewObject.setContractor("");
        memberViewObject.setShopName("");
        memberViewObject.setTemp(false);

        memberViewObject.setStatus(this.getStatus(memberProfile.getId(), referrer).getValue());
        return memberViewObject;
    }

    private MemberViewObject createByTempMember(TempMember tempMember, String referrer) {
        MemberViewObject memberViewObject = new MemberViewObject();
        memberViewObject.setTemp(true);
        memberViewObject.setMerchant(false);
        memberViewObject.setId(tempMember.getName());
        memberViewObject.setNickName(tempMember.getNickname());
        memberViewObject.setThumb(globalConfiguration.getDefaultThumb());
        memberViewObject.setStatus(this.getStatus(tempMember.getName(), referrer).getValue());
        return memberViewObject;
    }

    private TempMember createAndSave(String referrer) {
        TempMember tempMember = tempMemberDao.getLast();
        if (tempMember == null) {
            tempMember = new MongoTempMember();
        }
        MongoTempMember newTempMember = new MongoTempMember();
        newTempMember.setName("t" + this.getNextId(tempMember.getName()));
        newTempMember.setNickname("游客" + getNextId(tempMember.getName()));
        newTempMember.setPersistentDate(System.currentTimeMillis());
        newTempMember.setSource(referrer);
        tempMemberDao.save(newTempMember);
        return newTempMember;
    }

    private int getNextId(String name) {
        if (StringUtils.isEmpty(name)) {
            return START_TEMP_MEMBER_ID + 1;
        } else {
            int memberId = Integer.parseInt(name.substring(1));
            return memberId + 1;
        }
    }

    private boolean cacheExisted(String name, String referrer) {
        String cacheKey = name + referrer;
        if (memberCacheMap.containsKey(cacheKey)) {
            CacheMemberViewObject cacheMember = this.memberCacheMap.get(cacheKey);
            if (!cacheMember.isExpired()) {
                return true;
            }
        }
        return false;
    }

    private MemberViewObject getCachedMember(String name, String referrer) {
        String cacheKey = name + referrer;
        return this.memberCacheMap.get(cacheKey);
    }

    private void cacheAdd(TempMember tempMember, String referrer) {
        MemberViewObject memberViewObject = createByTempMember(tempMember, referrer);
        this.cacheAdd(memberViewObject, referrer);
    }

    private void cacheAdd(MemberViewObject memberViewObject, String referrer) {
        String cacheKey = memberViewObject.getId() + referrer;
        if (this.memberCacheMap.containsKey(cacheKey)) {
            this.memberCacheMap.remove(cacheKey);
        }
        this.memberCacheMap.put(cacheKey, new CacheMemberViewObject(memberViewObject));
    }
}
