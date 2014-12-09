package org.xlet.strawberry.core.member.relation;

import org.xlet.strawberry.core.exception.ServerInnerException;
import org.xlet.strawberry.core.member.BasicMember;

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
