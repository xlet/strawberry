package org.xlet.strawberry.thirdparty.web.sdk.model;

/**
 * Creator: JimmyLin
 * DateTime: 14-6-30 上午10:05
 * Summary: 登录信息
 */
public class Account {
    /**
     * w号
     */
    private String wid;
    /**
     * 明文密码
     */
    private String password;

    public Account() {
    }

    public Account(String wid, String password) {
        this.wid = wid;
        this.password = password;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
