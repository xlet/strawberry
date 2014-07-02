package cn.w.im.web.services.impl;

import cn.w.im.utils.sdk.usercenter.Members;
import cn.w.im.utils.sdk.usercenter.UcException;
import cn.w.im.utils.sdk.usercenter.UserCenterException;
import cn.w.im.utils.sdk.usercenter.model.MemberProfile;
import cn.w.im.web.GlobalConfiguration;
import cn.w.im.web.mongo.dao.TempMemberDao;
import cn.w.im.web.mongo.MongoTempMember;
import cn.w.im.web.services.MemberService;
import cn.w.im.web.vo.LinkmanViewObject;
import cn.w.im.web.vo.MemberViewObject;
import cn.w.im.web.vo.Status;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author jackie.
 */
@Service
public class MemberServiceImpl implements MemberService {

    private final static int START_TEMP_MEMBER_ID = 121639818;

    private final static Log LOG = LogFactory.getLog(MemberServiceImpl.class);

    @Autowired
    private GlobalConfiguration globalConfiguration;

    @Autowired
    private TempMemberDao tempMemberDao;

    private Members userCenterMembers = new cn.w.im.utils.sdk.usercenter.MemberService();

    public MemberServiceImpl() {
    }

    @Override
    public boolean existed(String fromId, String referrer) {
        //todo : jackie implement!
        return false;
    }

    @Override
    public LinkmanViewObject createTemp(String referrer) {
        MongoTempMember tempMember = createAndSave(referrer);
        LinkmanViewObject linkmanViewObject = new LinkmanViewObject();
        linkmanViewObject.setName(tempMember.getNickname());
        linkmanViewObject.setId(tempMember.getName());
        linkmanViewObject.setStatus(Status.Online);
        return linkmanViewObject;
    }

    @Override
    public MemberViewObject get(String name, String referrer) {

        if (StringUtils.isEmpty(name)){
            return new MemberViewObject();
        }

        MongoTempMember tempMember = tempMemberDao.getByName(name);
        if (tempMember!=null){
            return createByTempMember(tempMember);
        }

        try {
            MemberProfile memberProfile = userCenterMembers.getByWid(name);
            return createByUserCenter(memberProfile);
        } catch (UserCenterException e) {
            LOG.error(e);
        }
        //todo: jackie exception  memberNotFoundException
        return null;
    }

    private MemberViewObject createByUserCenter(MemberProfile memberProfile) {
        MemberViewObject memberViewObject = new MemberViewObject();
        memberViewObject.setThumb(memberProfile.getPhotoUrl());
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
        return memberViewObject;
    }

    private MemberViewObject createByTempMember(MongoTempMember tempMember) {
        MemberViewObject memberViewObject = new MemberViewObject();
        memberViewObject.setTemp(true);
        memberViewObject.setMerchant(false);
        memberViewObject.setId(tempMember.getName());
        memberViewObject.setNickName(tempMember.getNickname());
        memberViewObject.setThumb(globalConfiguration.getDefaultThumb());
        return memberViewObject;
    }

    private MongoTempMember createAndSave(String referrer) {
        MongoTempMember tempMember = tempMemberDao.getLast();
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
}
