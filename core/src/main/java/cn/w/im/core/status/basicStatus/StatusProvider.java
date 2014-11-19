package cn.w.im.core.status.basicStatus;

import cn.w.im.core.member.BasicMember;

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
