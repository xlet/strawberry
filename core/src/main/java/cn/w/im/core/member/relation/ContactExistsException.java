package cn.w.im.core.member.relation;

import cn.w.im.core.exception.ServerInnerException;

/**
 * contact exists exception.
 */
public class ContactExistsException extends ServerInnerException {

    private final String memberId;

    public ContactExistsException(String memberId) {
        super("contact[" + memberId + "] exists.");
        this.memberId = memberId;
    }

    public String getMemberId() {
        return memberId;
    }
}
