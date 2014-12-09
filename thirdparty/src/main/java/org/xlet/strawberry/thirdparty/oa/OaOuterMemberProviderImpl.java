package org.xlet.strawberry.thirdparty.oa;

import org.xlet.strawberry.core.member.BasicMember;
import org.xlet.strawberry.core.member.SexType;
import org.xlet.strawberry.core.member.relation.FriendGroup;
import org.xlet.strawberry.core.ProductType;
import org.xlet.strawberry.core.member.provider.GetMemberErrorException;
import org.xlet.strawberry.core.member.provider.IdPasswordException;
import org.xlet.strawberry.core.member.provider.MemberNotExistedException;
import org.xlet.strawberry.core.member.provider.OuterMemberProvider;
import org.xlet.strawberry.thirdparty.oa.sdk.OaMemberService;
import org.xlet.strawberry.thirdparty.oa.sdk.domain.OaMemberProfile;
import org.xlet.strawberry.thirdparty.oa.sdk.request.GetOrganizationMemberRequest;
import org.xlet.strawberry.thirdparty.oa.sdk.request.VerifyRequest;
import org.xlet.strawberry.thirdparty.oa.sdk.response.GetOrganizationMemberResponse;
import org.xlet.strawberry.thirdparty.oa.sdk.response.VerifyResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * oa api wrapper.
 */
@Component("oaOuterMemberProvider")
public class OaOuterMemberProviderImpl implements OuterMemberProvider {

    /**
     * system group cached map.
     * <p/>
     * key:orgCode.
     */
    private Map<String, List<FriendGroup>> systemGroupMap;

    private static final Logger LOGGER = LoggerFactory.getLogger(OaOuterMemberProviderImpl.class);

    @Autowired
    private OaMemberService oaMemberService;

    public OaOuterMemberProviderImpl() {
        systemGroupMap = new ConcurrentHashMap<String, List<FriendGroup>>();
    }

    @Override
    public BasicMember verify(String loginId, String password) throws IdPasswordException {
        try {
            VerifyRequest request = new VerifyRequest(loginId, password);
            VerifyResponse response = oaMemberService.verify(request);
            if (!response.isSuccess()) {
                throw new IdPasswordException(loginId);
            }
            return this.createOaMember(response.getUserInfo());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new IdPasswordException(loginId);
        }
    }

    @Override
    public BasicMember get(String memberId) throws MemberNotExistedException, GetMemberErrorException {
        throw new GetMemberErrorException(memberId, ProductType.OA);
    }

    public List<BasicMember> getOrgMembers(String orgCode) throws GetMemberErrorException {
        List<BasicMember> members = new ArrayList<BasicMember>();
        GetOrganizationMemberRequest request = new GetOrganizationMemberRequest(orgCode);
        try {
            GetOrganizationMemberResponse response = oaMemberService.getMembers(request);
            for (OaMemberProfile memberProfile : response.getUsers()) {
                OAMember oaMember = this.createOaMember(memberProfile);
                members.add(oaMember);
            }
            return members;
        } catch (Exception e) {
            throw new GetMemberErrorException(orgCode, ProductType.OA, e);
        }
    }

    @Override
    public Collection<FriendGroup> getSystemGroup(BasicMember owner) throws GetMemberErrorException {
        if (!(owner instanceof OAMember)) {
            return new ArrayList<FriendGroup>();
        }

        String orgCode = ((OAMember) owner).getOrgCode();
        if (this.systemGroupMap.containsKey(orgCode)) {
            return this.systemGroupMap.get(orgCode);
        }
        Map<String, FriendGroup> friendGroupMap = new HashMap<String, FriendGroup>();
        List<BasicMember> relativeMembers = this.getOrgMembers(orgCode);
        for (BasicMember member : relativeMembers) {
            OAMember oaMember = (OAMember) member;
            if (member != null && !StringUtils.isEmpty(((OAMember) member).getDepartment())) {
                if (!friendGroupMap.containsKey(oaMember.getDepartment())) {
                    FriendGroup friendGroup = new FriendGroup("", oaMember.getDepartment(), owner, true);
                    friendGroup.initContact(oaMember);
                    friendGroupMap.put(oaMember.getDepartment(), friendGroup);
                } else {
                    FriendGroup friendGroup = friendGroupMap.get(oaMember.getDepartment());
                    friendGroup.initContact(oaMember);
                }
            }
        }
        if (!this.systemGroupMap.containsKey(orgCode)) {
            List<FriendGroup> cachedFriendGroup = new CopyOnWriteArrayList<FriendGroup>(friendGroupMap.values());
            this.systemGroupMap.put(orgCode, cachedFriendGroup);
        }
        return friendGroupMap.values();
    }

    public OAMember createOaMember(OaMemberProfile oaMember) {
        OAMember member = new OAMember();
        member.setMobile(oaMember.getMobile());
        member.setId(oaMember.getId());
        member.setEmail(oaMember.getEmail());
        member.setAddress(oaMember.getAddress());
        member.setNickname(oaMember.getUserName());
        member.setPicUrl(oaMember.getUserPic());
        member.setTelephone(oaMember.getTelephone());
        if (oaMember.getUserSex().equals("男")) {
            member.setSex(SexType.Male);
        } else {
            member.setSex(SexType.Female);
        }
        member.setPost(oaMember.getPost());
        member.setDepartment(oaMember.getDepartment());
        return member;
    }
}
