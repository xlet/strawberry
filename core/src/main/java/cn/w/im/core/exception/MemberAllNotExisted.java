package cn.w.im.core.exception;

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
