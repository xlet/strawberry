package org.xlet.strawberry.thirdparty.web;

import org.xlet.strawberry.core.member.BasicMember;
import org.xlet.strawberry.core.member.SexType;
import org.xlet.strawberry.core.member.relation.FriendGroup;
import org.xlet.strawberry.core.ProductType;
import org.xlet.strawberry.core.member.provider.GetMemberErrorException;
import org.xlet.strawberry.core.member.provider.IdPasswordException;
import org.xlet.strawberry.core.member.provider.MemberNotExistedException;
import org.xlet.strawberry.core.member.provider.OuterMemberProvider;
import org.xlet.strawberry.thirdparty.web.sdk.UserCenterException;
import org.xlet.strawberry.thirdparty.web.sdk.WebMemberService;
import org.xlet.strawberry.thirdparty.web.sdk.model.Account;
import org.xlet.strawberry.thirdparty.web.sdk.model.MemberProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * web api wrapper.
 */
@Component("wcnOuterMemberProvider")
public class WebOuterMemberProviderImpl implements OuterMemberProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebOuterMemberProviderImpl.class);

    @Autowired
    private WebMemberService wcnMemberService;

    public WebOuterMemberProviderImpl() {
    }

    @Override
    public BasicMember verify(String loginId, String password) throws IdPasswordException {
        try {
            Account account = new Account(loginId, password);
            boolean success = wcnMemberService.verify(account);
            if (!success) {
                throw new IdPasswordException(loginId);
            }
            MemberProfile memberProfile = wcnMemberService.getByWid(loginId);
            return this.createWcnMember(memberProfile);
        } catch (UserCenterException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IdPasswordException(loginId);
        }
    }

    @Override
    public BasicMember get(String memberId) throws MemberNotExistedException, GetMemberErrorException {
        try {
            MemberProfile memberProfile = wcnMemberService.getByWid(memberId);
            if (memberProfile != null) {
                WebMember member = this.createWcnMember(memberProfile);
                return member;
            }
            throw new MemberNotExistedException(memberId, ProductType.WCN);
        } catch (UserCenterException e) {
            throw new GetMemberErrorException(memberId, ProductType.WCN, e);
        }
    }

    @Override
    public Collection<FriendGroup> getSystemGroup(BasicMember owner) throws GetMemberErrorException {
        throw new GetMemberErrorException(owner.getId(), ProductType.WCN);
    }

    public WebMember createWcnMember(MemberProfile profile) {
        WebMember member = new WebMember();
        member.setEmail(profile.getEmail());
        member.setAddress(profile.getAddress());
        member.setId(profile.getWid());
        member.setMobile(profile.getMobile());
        member.setMerchant(profile.isMerchant());
        member.setNickname(profile.getNickname());
        member.setRealNameValid(profile.isRealNameValid());
        member.setMobileValid(profile.isMobileValid());
        member.setPicUrl(profile.getPhotoUrl());
        if (profile.getSexType().equals("1")) {
            member.setSex(SexType.Male);
        } else {
            member.setSex(SexType.Female);
        }
        return member;
    }
}
