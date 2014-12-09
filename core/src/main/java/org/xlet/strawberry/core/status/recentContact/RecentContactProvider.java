package org.xlet.strawberry.core.status.recentContact;

import org.xlet.strawberry.core.member.BasicMember;

/**
 * recent contact status provider.
 */
public interface RecentContactProvider {

    RecentContactStatuses get(BasicMember owner);

    void change(BasicMember owner, BasicMember contact, String content);

    void onMemberLogout(BasicMember member);
}
