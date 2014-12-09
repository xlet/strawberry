package org.xlet.strawberry.core.status.memberAll;

import org.xlet.strawberry.core.exception.ServerInnerException;

/**
 * member all not existed.
 */
public class MemberAllNotExisted extends ServerInnerException {

    private String memberId;

    public MemberAllNotExisted(String memberId) {
        super("the memberAll[" + memberId + "] is not existed!");
        this.memberId = memberId;
    }

    public String getMemberId() {
        return memberId;
    }
}
