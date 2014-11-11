package cn.w.im.thirdparty.oa.sdk;

import org.apache.http.impl.client.CloseableHttpClient;

/**
 * oa config.
 */
public class OaConfig {

    private String baseUrl;
    private CloseableHttpClient httpClient;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }
}
