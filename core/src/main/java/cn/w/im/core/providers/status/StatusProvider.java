package cn.w.im.core.providers.status;

import cn.w.im.core.Status;
import cn.w.im.core.member.BasicMember;
import cn.w.im.core.member.MemberStatus;

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
