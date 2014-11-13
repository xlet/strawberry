package cn.w.im.core.providers.relation;

import cn.w.im.core.member.BasicMember;
import cn.w.im.core.message.client.ProductType;
import cn.w.im.core.member.relation.FriendGroup;
import cn.w.im.core.member.relation.RecentContactStatuses;
import cn.w.im.core.member.MemberStatus;
import cn.w.im.core.exception.ContactNotExistedException;

import java.util.Collection;
import java.util.List;

/**
 * contact provider.
 */
public interface ContactProvider {

    /**
     * get member's friend groups.
     *
     * @param owner owner member.
     * @return member's friend groups.
     */
    Collection<FriendGroup> getFriendGroup(BasicMember owner);

    BasicMember getContact(String memberId) throws ContactNotExistedException;
}
