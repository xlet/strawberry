package cn.w.im.server;

import cn.w.im.domains.basic.Member;
import cn.w.im.domains.basic.NearlyLinkman;
import cn.w.im.persistent.NearlyLinkmanDao;
import cn.w.im.server.cache.member.MemberCacheProvider;
import cn.w.im.utils.sdk.usercenter.Members;
import cn.w.im.utils.sdk.usercenter.UserCenterException;
import cn.w.im.utils.sdk.usercenter.model.MemberProfile;
import cn.w.im.utils.spring.SpringContext;

import java.util.ArrayList;
import java.util.List;

/**
 * default linkman provider.
 */
public class DefaultLinkmanProviderImpl implements LinkmanProvider {

    private MemberCacheProvider memberCacheProvider = new MemberCacheProvider();

    private NearlyLinkmanDao nearlyLinkmanDao;

    private Members memberService;

    @Override
    public List<Member> getNearlyLinkmen(String memberId) {
        List<NearlyLinkman> linkmen = nearlyLinkmanDaoReady().get(memberId, 20, 1);
        List<Member> recent = new ArrayList<Member>();
        if (linkmen != null && linkmen.size() > 0) {
            for (NearlyLinkman linkman : linkmen) {
                String wid = linkman.toggle(memberId);
                if(wid == null || "".equals(wid)){
                    continue;
                }
                recent.add(getMember(wid));
            }
        }
        return recent;
    }

    @Override
    public Member getMember(String memberId) {

        if (memberCacheProvider.cacheExisted(memberId)) {
            return memberCacheProvider.getCachedMember(memberId);
        } else {
            try {
                MemberProfile memberProfile = membersReady().getByWid(memberId);
                Member lightweight = lightweight(memberProfile);
                memberCacheProvider.cacheAdd(lightweight, null);
                return lightweight;
            } catch (UserCenterException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<Member> getMembers(List<String> ids) {
        List<Member> members = new ArrayList<Member>();
        for (String id : ids) {
            if(id == null || "".equals(id)){
                continue;
            }
            Member member = getMember(id);
            if (member != null) {
                members.add(member);
            }
        }
        return members;
    }


    private Member lightweight(MemberProfile profile) {
        Member member = new Member();
        member.setEmail(profile.getEmail());
        member.setAddress(profile.getAddress());
        member.setId(profile.getWid());
        member.setMobile(profile.getMobile());
        member.setMerchant(profile.isMerchant());
        member.setNickName(profile.getNickname());
        member.setRealNameValid(profile.isRealNameValid());
        member.setMobileValid(profile.isMobileValid());
        member.setThumb(profile.getPhotoUrl());
        member.setTemp(false);
        return member;
    }


    private NearlyLinkmanDao nearlyLinkmanDaoReady() {
        if (nearlyLinkmanDao == null) {
            nearlyLinkmanDao = SpringContext.context().getBean("mongoNearlyLinkmanDao", NearlyLinkmanDao.class);
        }
        return nearlyLinkmanDao;
    }

    private Members membersReady() {
        if (memberService == null) {
            memberService = SpringContext.context().getBean("members", Members.class);
        }
        return memberService;
    }


}
