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

    /**
     * get recent contact.
     *
     * @param owner owner member.
     * @return recent contact.
     */
    RecentContactStatuses getRecentContact(BasicMember owner);

    /**
     * get member info.
     *
     * @param productType product type.
     * @param memberId    member id.
     * @return member info.
     */
    BasicMember getContact(String memberId, ProductType productType);

    BasicMember getContact(String memberId) throws ContactNotExistedException;

    /**
     * @param ids
     * @return
     */
    List<BasicMember> getMembers(List<String> ids);

    /**
     * get contact member statuses.
     *
     * @param self owner id.
     * @return contact member statuses.
     */
    Collection<MemberStatus> getContactStatus(BasicMember self);
}
