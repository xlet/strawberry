package cn.w.im.core.providers.member;

import cn.w.im.domains.member.BasicMember;
import cn.w.im.domains.member.OAMember;
import cn.w.im.domains.member.WcnMember;
import cn.w.im.domains.messages.client.ProductType;
import cn.w.im.exceptions.GetMemberErrorException;
import cn.w.im.exceptions.IdPasswordException;
import cn.w.im.exceptions.MemberNotExistedException;
import cn.w.im.utils.sdk.oa.OaMemberService;
import cn.w.im.utils.sdk.oa.domain.OaMemberProfile;
import cn.w.im.utils.sdk.oa.request.GetOrganizationMemberRequest;
import cn.w.im.utils.sdk.oa.request.VerifyRequest;
import cn.w.im.utils.sdk.oa.response.GetOrganizationMemberResponse;
import cn.w.im.utils.sdk.oa.response.VerifyResponse;
import cn.w.im.utils.sdk.usercenter.UserCenterException;
import cn.w.im.utils.sdk.usercenter.WcnMemberService;
import cn.w.im.utils.sdk.usercenter.model.Account;
import cn.w.im.utils.sdk.usercenter.model.MemberProfile;
import cn.w.im.utils.spring.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * default implement of MemberProvider.
 */
public class DefaultMemberProviderImpl implements MemberProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMemberProviderImpl.class);

    /**
     * member cache.
     * <p/>
     * key:productType+@@+memberId
     */
    private Map<String, BasicMember> memberCacheMap;

    private WcnMemberService wcnMemberService;

    private OaMemberService oaMemberService;
    private OuterMemberSupport outerMemberSupport;

    public DefaultMemberProviderImpl() {
        this.memberCacheMap = new ConcurrentHashMap<String, BasicMember>();
        this.outerMemberSupport = new OuterMemberSupport();
        this.wcnMemberService = SpringContext.context().getBean("wcnMemberService", WcnMemberService.class);
        this.oaMemberService = SpringContext.context().getBean("oaMemberService", OaMemberService.class);
    }

    @Override
    public BasicMember verify(String loginId, String password, ProductType productType) throws IdPasswordException {
        switch (productType) {
            case OA:
                return verifyByOa(loginId, password);
            case WCN:
                return verifyByWcn(loginId, password);
            default:
                throw new IdPasswordException(loginId);
        }
    }

    @Override
    public BasicMember get(String memberId, ProductType productType) throws MemberNotExistedException, GetMemberErrorException {
        String key = this.getCachedKey(memberId, productType);
        if (this.memberCacheMap.containsKey(key)) {
            return this.memberCacheMap.get(key);
        }
        switch (productType) {
            case OA:
                return getByOa(memberId);
            case WCN:
                return getByWcn(memberId);
            default:
                throw new MemberNotExistedException(memberId, productType);
        }
    }

    @Override
    public void addMember(BasicMember basicMember, ProductType productType) {
        String cacheMemberKey = this.getCachedKey(basicMember.getId(), productType);
        if (!this.memberCacheMap.containsKey(cacheMemberKey)) {
            this.memberCacheMap.put(cacheMemberKey, basicMember);
        }
        //todo:jackie check member changed.
    }

    @Override
    public List<BasicMember> searchById(String idString, ProductType productType) throws GetMemberErrorException {
        switch (productType) {
            case OA:
                return getOaOrganizationMembers(idString);
            case WCN:
                //toDo:jackie add wcn member search.
                return new ArrayList<BasicMember>();
            default:
                return new ArrayList<BasicMember>();
        }
    }

    private BasicMember getByOa(String memberId) throws MemberNotExistedException, GetMemberErrorException {
        String getKey = this.getCachedKey(memberId, ProductType.OA);
        if (this.memberCacheMap.containsKey(getKey)) {
            return this.memberCacheMap.get(getKey);
        }

        if (!memberId.contains(":")) {
            throw new IllegalArgumentException(memberId + " is not oa member id.");
        }

        String orgCode = memberId.split(":")[0];
        getOaOrganizationMembers(orgCode);
        if (this.memberCacheMap.containsKey(getKey)) {
            return this.memberCacheMap.get(getKey);
        }
        throw new MemberNotExistedException(memberId, ProductType.OA);
    }

    private List<BasicMember> getOaOrganizationMembers(String orgCode) throws GetMemberErrorException {
        List<BasicMember> members = new ArrayList<BasicMember>();
        GetOrganizationMemberRequest request = new GetOrganizationMemberRequest(orgCode);
        try {
            GetOrganizationMemberResponse response = oaMemberService.getMembers(request);
            for (OaMemberProfile memberProfile : response.getUsers()) {
                OAMember oaMember = this.outerMemberSupport.createOaMember(memberProfile);
                members.add(oaMember);
                String oaMemberKey = this.getCachedKey(oaMember.getId(), ProductType.OA);
                if (!this.memberCacheMap.containsKey(oaMemberKey)) {
                    this.memberCacheMap.put(oaMemberKey, oaMember);
                }
            }
            return members;
        } catch (Exception e) {
            throw new GetMemberErrorException(orgCode, ProductType.OA, e);
        }
    }

    private String getCachedKey(String memberId, ProductType productType) {
        return productType.toString() + "@@" + memberId;
    }

    private BasicMember getByWcn(String memberId) throws MemberNotExistedException, GetMemberErrorException {
        try {
            MemberProfile memberProfile = wcnMemberService.getByWid(memberId);
            WcnMember member = outerMemberSupport.createWcnMember(memberProfile);
            if (member != null) {
                String cacheKey = this.getCachedKey(memberId, ProductType.WCN);
                if (!this.memberCacheMap.containsKey(cacheKey)) {
                    this.memberCacheMap.put(cacheKey, member);
                }
                return member;
            }
            throw new MemberNotExistedException(memberId, ProductType.WCN);
        } catch (UserCenterException e) {
            throw new GetMemberErrorException(memberId, ProductType.WCN, e);
        }
    }

    private BasicMember verifyByWcn(String loginId, String password) throws IdPasswordException {
        try {
            Account account = new Account(loginId, password);
            boolean success = wcnMemberService.verify(account);
            if (!success) {
                throw new IdPasswordException(loginId);
            }
            MemberProfile memberProfile = wcnMemberService.getByWid(loginId);
            //todo:member changed compare
            return outerMemberSupport.createWcnMember(memberProfile);
        } catch (UserCenterException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IdPasswordException(loginId);
        }
    }

    private BasicMember verifyByOa(String loginId, String password) throws IdPasswordException {
        try {
            VerifyRequest request = new VerifyRequest(loginId, password);
            VerifyResponse response = oaMemberService.verify(request);
            if (!response.isSuccess()) {
                throw new IdPasswordException(loginId);
            }
            return outerMemberSupport.createOaMember(response.getUserInfo());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new IdPasswordException(loginId);
        }
    }
}
