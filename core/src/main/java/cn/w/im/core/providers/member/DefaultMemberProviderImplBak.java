package cn.w.im.core.providers.member;

import cn.w.im.domains.member.BasicMember;
import cn.w.im.domains.member.OAMember;
import cn.w.im.exceptions.IdPasswordException;
import cn.w.im.persistent.OaMemberDao;
import cn.w.im.persistent.TempMemberDao;
import cn.w.im.persistent.WcnMemberDao;
import cn.w.im.utils.sdk.oa.OaMemberService;
import cn.w.im.utils.sdk.oa.domain.OaMemberProfile;
import cn.w.im.utils.sdk.oa.request.VerifyRequest;
import cn.w.im.utils.sdk.oa.response.VerifyResponse;
import cn.w.im.utils.sdk.usercenter.WcnMemberService;
import cn.w.im.utils.spring.SpringContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * default member provider.
 */
public class DefaultMemberProviderImplBak {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMemberProviderImplBak.class);

    private Map<String, BasicMember> cachedMemberMap;

    private TempMemberDao tempMemberDao;

    private OaMemberDao oaMemberDao;

    private WcnMemberDao wcnMemberDao;

    private WcnMemberService outMemberService;

    private OaMemberService oaMemberService;

    public DefaultMemberProviderImplBak() {
        this.cachedMemberMap = new ConcurrentHashMap<String, BasicMember>();
        this.tempMemberDao = SpringContext.context().getBean(TempMemberDao.class);
        this.oaMemberDao = SpringContext.context().getBean(OaMemberDao.class);
        this.wcnMemberDao = SpringContext.context().getBean(WcnMemberDao.class);
        this.oaMemberService = SpringContext.context().getBean(OaMemberService.class);
        this.outMemberService = SpringContext.context().getBean(WcnMemberService.class);
    }

    public BasicMember get(String memberId) {
        if (cachedMemberMap.containsKey(memberId)) {
            return cachedMemberMap.get(memberId);
        } else {
            BasicMember member = getFromDatabase(memberId);
            if (member != null) {
                this.cachedMemberMap.put(member.getId(), member);
                return member;
            } else {
                BasicMember outMember = getFromOuterSystem(memberId);
            }
        }
        return null;
    }

    public boolean verify(String memberId, String password) throws IdPasswordException {
        if (!StringUtils.isEmpty(memberId) && memberId.contains(":")) {
            return verifyByOa(memberId, password);
        } else {
            return false;
        }
    }

    private boolean verifyByOa(String memberId, String password) throws IdPasswordException {
        try {
            VerifyRequest request = new VerifyRequest(memberId, password);
            VerifyResponse response = oaMemberService.verify(request);
            if (!response.isSuccess()) {
                throw new IdPasswordException(memberId);
            } else {
                BasicMember member = wrapperMember(response);
                cacheMember(member);
                return true;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new IdPasswordException(memberId);
        }
    }

    private void cacheMember(BasicMember member) {
        if (!cachedMemberMap.containsKey(member.getId())) {
            this.cachedMemberMap.put(member.getId(), member);
        } else {
            cachedMemberMap.remove(member.getId());
            //toDo  compare whether changed!
            cachedMemberMap.put(member.getId(), member);
        }
    }

    private BasicMember wrapperMember(VerifyResponse response) {
        OaMemberProfile oaMember = response.getUserInfo();
        OAMember member = new OAMember();
        member.setMobile(oaMember.getMobile());
        member.setId(oaMember.getId());
        member.setEmail(oaMember.getEmail());
        member.setAddress(oaMember.getAddress());
        member.setNickname(oaMember.getUserName());
        member.setPicUrl(oaMember.getUserPic());
        member.setTelephone(oaMember.getTelephone());
        return member;
    }

    private BasicMember getFromOuterSystem(String memberId) {
        return null;
    }

    private BasicMember getFromDatabase(String memberId) {
        if (memberId.contains(":")) {
            BasicMember member = oaMemberDao.get(memberId);
            if (member != null) {
                return member;
            }
        }
        BasicMember member = wcnMemberDao.get(memberId);
        if (member != null) {
            return member;
        }
        return tempMemberDao.get(memberId);
    }
}
