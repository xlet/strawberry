package org.xlet.strawberry.thirdparty.web.sdk.config;

import org.apache.http.impl.client.CloseableHttpClient;

/**
 * Creator: JimmyLin
 * DateTime: 14-6-30 上午9:44
 * Summary: 用户中心SDK配置
 */
public class UcConfig {

    private String baseUrl;
    private String secret;

    private CloseableHttpClient httpClient;

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

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }
}
