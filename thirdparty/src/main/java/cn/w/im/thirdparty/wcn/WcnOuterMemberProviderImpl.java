package cn.w.im.thirdparty.wcn;

import cn.w.im.core.member.BasicMember;
import cn.w.im.core.member.SexType;
import cn.w.im.core.member.relation.FriendGroup;
import cn.w.im.core.ProductType;
import cn.w.im.core.member.provider.GetMemberErrorException;
import cn.w.im.core.member.provider.IdPasswordException;
import cn.w.im.core.member.provider.MemberNotExistedException;
import cn.w.im.core.member.provider.OuterMemberProvider;
import cn.w.im.thirdparty.wcn.sdk.UserCenterException;
import cn.w.im.thirdparty.wcn.sdk.WcnMemberService;
import cn.w.im.thirdparty.wcn.sdk.model.Account;
import cn.w.im.thirdparty.wcn.sdk.model.MemberProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * wcn api wrapper.
 */
@Component("wcnOuterMemberProvider")
public class WcnOuterMemberProviderImpl implements OuterMemberProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(WcnOuterMemberProviderImpl.class);

    @Autowired
    private WcnMemberService wcnMemberService;

    public WcnOuterMemberProviderImpl() {
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
                WcnMember member = this.createWcnMember(memberProfile);
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

    public WcnMember createWcnMember(MemberProfile profile) {
        WcnMember member = new WcnMember();
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
