package cn.w.im.utils.sdk.usercenter.config;

/**
 * Creator: JimmyLin
 * DateTime: 14-6-30 上午9:44
 * Summary: 用户中心SDK配置
 */
public class UcConfig {

    private String baseUrl;
    private String secret;

    /**
     * get user center api base url.
     *
     * @return the url
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }


    /**
     * get user center api signature secret.
     *
     * @return the secret
     */
    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
