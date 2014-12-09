package org.xlet.strawberry.core.status.basicStatus;

import org.xlet.strawberry.core.member.BasicMember;

/**
 * member status.
 */
public class MemberStatus {

    //todo:jackie member status with client type refactor.

    private BasicMember member;
    private Status status;

    /**
     * constructor.
     *
     * @param member member.
     * @param status status.
     */
    public MemberStatus(BasicMember member, Status status) {
        this.member = member;
        this.status = status;
    }

    public BasicMember getMember() {
        return member;
    }

    public void setMember(BasicMember member) {
        this.member = member;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
