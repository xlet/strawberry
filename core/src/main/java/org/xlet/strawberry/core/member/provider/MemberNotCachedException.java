package org.xlet.strawberry.core.member.provider;

import org.xlet.strawberry.core.exception.ServerInnerException;

/**
 * member not in cache exception.
 */
public class MemberNotCachedException extends ServerInnerException {

    private String memberId;

    public MemberNotCachedException(String memberId) {
        super("member[" + memberId + "] is not in member cache.");
        this.memberId = memberId;
    }

    public String getMemberId() {
        return memberId;
    }
}
