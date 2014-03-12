package cn.w.im.domains;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-8 下午4:57.
 * Summary:
 */
public class LoginToken {

    private String clientIp;

    private String loginId;

    private long loginDate;

    private String token;

    /**
     * 获取客户端Ip地址.
     * @return ip地址.
     */
    public String getClientIp() {
        return clientIp;
    }

    /**
     * 设置客户端Ip地址.
     * @param clientIp 客户端ip地址.
     */
    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    /**
     * 获取登陆id.
     * @return 登陆id.
     */
    public String getLoginId() {
        return loginId;
    }

    /**
     * 获取登陆id.
     * @param loginId 登陆id.
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    /**
     * 获取登陆时间.
     * @return 登陆时间.
     */
    public long getLoginDate() {
        return loginDate;
    }

    /**
     * 设置登陆时间.
     * @param loginDate 登陆时间.
     */
    public void setLoginDate(long loginDate) {
        this.loginDate = loginDate;
    }

    /**
     * 获取标识.
     * @return 标识.
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置标识.
     * @param token 标识．
     */
    public void setToken(String token) {
        this.token = token;
    }
}
