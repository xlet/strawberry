package cn.w.im.domains.common;

/**
 * online member status.
 */
public class OnlineMemberStatus {

    private String loginId;
    private Status status;

    public OnlineMemberStatus() {

    }

    /**
     * constructor.
     *
     * @param loginId login id.
     * @param status  status.
     */
    public OnlineMemberStatus(String loginId, Status status) {
        this.loginId = loginId;
        this.status = status;
    }

    /**
     * get login id.
     * @return login id.
     */
    public String getLoginId() {
        return loginId;
    }

    /**
     * set login id.
     * @param loginId login id.
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    /**
     * get status.
     * @return status.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * set status.
     * @param status status.
     */
    public void setStatus(Status status) {
        this.status = status;
    }
}
