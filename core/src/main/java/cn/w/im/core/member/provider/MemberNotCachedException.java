package cn.w.im.core.member.provider;

import cn.w.im.core.exception.ServerInnerException;

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
