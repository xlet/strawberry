package cn.w.im.core.providers.status;

import cn.w.im.core.member.BasicMember;
import cn.w.im.core.member.relation.RecentContactStatuses;

/**
 * recent contact status provider.
 */
public interface RecentContactProvider {

    RecentContactStatuses get(BasicMember owner);

    void change(BasicMember owner, BasicMember contact, String content);

    void onMemberLogout(BasicMember member);
}
