package cn.w.im.core.member.relation;

import cn.w.im.core.member.provider.MemberNotCachedException;
import cn.w.im.core.exception.ServerInnerException;

/**
 * contact is not existed exception.
 */
public class ContactNotExistedException extends ServerInnerException {

    private String memberId;

    public ContactNotExistedException(MemberNotCachedException ex) {
        super("contact[" + ex.getMemberId() + "] is not existed.", ex);
        this.memberId = ex.getMemberId();
    }

    public ContactNotExistedException(String memberId) {
        super("contact[" + memberId + "] is not existed.");
        this.memberId = memberId;
    }

    public String getMemberId() {
        return memberId;
    }
}
