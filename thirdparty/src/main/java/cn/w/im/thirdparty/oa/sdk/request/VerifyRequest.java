package cn.w.im.thirdparty.oa.sdk.request;

/**
 * verify request.
 */
public class VerifyRequest {

    private String id;
    private String password;

    public VerifyRequest(String memberId, String password) {
        this.id = memberId;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
