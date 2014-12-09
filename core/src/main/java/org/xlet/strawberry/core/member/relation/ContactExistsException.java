package org.xlet.strawberry.core.member.relation;

import org.xlet.strawberry.core.exception.ServerInnerException;

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
