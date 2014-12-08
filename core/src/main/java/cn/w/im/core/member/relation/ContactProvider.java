package cn.w.im.core.member.relation;

import cn.w.im.core.exception.ServerInnerException;
import cn.w.im.core.member.BasicMember;

import java.util.Collection;

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
    Collection<FriendGroup> getFriendGroup(BasicMember owner) throws ServerInnerException;

    BasicMember getContact(String memberId) throws ContactNotExistedException;
}
