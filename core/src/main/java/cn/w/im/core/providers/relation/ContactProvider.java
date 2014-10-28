package cn.w.im.core.providers.relation;

import cn.w.im.domains.member.BasicMember;
import cn.w.im.domains.messages.client.ProductType;
import cn.w.im.domains.relation.FriendGroup;
import cn.w.im.domains.relation.RecentContacts;

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
    RecentContacts getRecentContact(BasicMember owner);

    /**
     * get member info.
     *
     * @param productType product type.
     * @param memberId    member id.
     * @return member info.
     */
    BasicMember getMember(String memberId, ProductType productType);

    List<BasicMember> getMembers(List<String> ids);
}
