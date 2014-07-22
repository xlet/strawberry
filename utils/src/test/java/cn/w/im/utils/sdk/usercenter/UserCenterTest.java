package cn.w.im.utils.sdk.usercenter;

import cn.w.im.utils.sdk.usercenter.config.UcConfig;
import cn.w.im.utils.sdk.usercenter.model.Account;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * Creator: JimmyLin
 * DateTime: 14-7-17 下午5:59
 * Summary:
 */
public class UserCenterTest {

    private MemberService members;
    private CloseableHttpClient httpClient;

    @Before
    public void init() {
        UcConfig ucConfig = new UcConfig();
        ucConfig.setBaseUrl("http://10.0.41.112/api/");
        ucConfig.setSecret("!1a@2b#3c$4d5e6f7g9");
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
        ucConfig.setHttpClient(httpClient);
        members = new MemberService();
        members.setConfig(ucConfig);
    }

    @Test
    public void verify() {
        for (int i = 0; i < 2; i++) {
            try {
                System.out.println(members.verify(new Account("11223344", "w123456")));
            } catch (UserCenterException e) {
                e.printStackTrace();
            }
        }
    }
}
