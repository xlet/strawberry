package cn.w.im.core.exception;

/**
 * contact is not existed exception.
 */
public class ContactNotExistedException extends ServerInnerException {

    public ContactNotExistedException(MemberNotCachedException ex) {
        super("contact[" + ex.getMemberId() + "] is not existed.", ex);
    }
}
