package cn.w.im.core.providers.member;

import cn.w.im.domains.member.OAMember;
import cn.w.im.domains.member.SexType;
import cn.w.im.domains.member.WcnMember;
import cn.w.im.utils.sdk.oa.domain.OaMemberProfile;
import cn.w.im.utils.sdk.usercenter.model.MemberProfile;

/**
 * outer member support.
 */
public class OuterMemberSupport {

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
