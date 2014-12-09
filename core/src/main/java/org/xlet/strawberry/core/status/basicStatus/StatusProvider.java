package org.xlet.strawberry.core.status.basicStatus;

import org.xlet.strawberry.core.member.BasicMember;

/**
 * member status provider.
 */
public interface StatusProvider {

    /**
     * get member status.
     *
     * @param member member.
     * @return member status.
     */
    MemberStatus status(BasicMember member);

    /**
     * change member status.
     *
     * @param member member.
     * @param status status.
     */
    void change(BasicMember member, Status status);

    /**
     * member is online.
     *
     * @param member member.
     * @return true: online.
     */
    boolean online(BasicMember member);
}
