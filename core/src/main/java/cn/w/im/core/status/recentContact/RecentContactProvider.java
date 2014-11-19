package cn.w.im.core.status.recentContact;

import cn.w.im.core.member.BasicMember;

/**
 * recent contact status provider.
 */
public interface RecentContactProvider {

    RecentContactStatuses get(BasicMember owner);

    void change(BasicMember owner, BasicMember contact, String content);

    void onMemberLogout(BasicMember member);
}
