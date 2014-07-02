package cn.w.im.utils.sdk.usercenter;

import cn.w.im.utils.sdk.usercenter.config.UcConfig;
import cn.w.im.utils.sdk.usercenter.model.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.*;

/**
 * Creator: JimmyLin
 * DateTime: 14-6-30 上午10:30
 * Summary:
 */
public class UserCenterSupport {

    private static final int SOCKET_TIMEOUT = 20000;
    private static final int CONNECT_TIMEOUT = 20000;

    private final ObjectMapper mapper = new ObjectMapper();

    private CloseableHttpClient httpClient;

    private void init() {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
    }

    public UserCenterSupport() {
        init();
    }

    protected <T> T get(String url, Class<T> clazz) throws UserCenterException {
        String resp = "";
        try {
            resp = getAsString(url, null);
            return mapper.readValue(resp, clazz);
        } catch (IOException e) {
            try {
                Response response = mapper.readValue(resp, Response.class);
                throw new UserCenterException(response);
            } catch (IOException e1) {
                throw new UserCenterException(e1.getMessage() + ":" + resp);
            }
        }
    }

    protected <T> T post(String url, Map<String, String> params, Class<T> clazz) throws UserCenterException {
        String resp = "";
        try {
            resp = postAsString(url, params);
            return mapper.readValue(resp, clazz);
        } catch (IOException e) {
            throw new UserCenterException(e.getMessage() + ":" + resp);
        }
    }

    private String execute(HttpUriRequest request) throws IOException, UserCenterException {
        CloseableHttpResponse httpResponse = httpClient.execute(request);
        int httpStatus = httpResponse.getStatusLine().getStatusCode();
        if (httpStatus == 200) {
            return EntityUtils.toString(httpResponse.getEntity());
        }
        throw new UserCenterException("request error, code[" + httpStatus + "].");
    }

    private String postAsString(String url, Map<String, String> params) throws IOException, UserCenterException {
        HttpPost httpPost = new HttpPost(url);
        HttpEntity httpEntity = new UrlEncodedFormEntity(toNvps(sign(url, params)), "utf-8");
        httpPost.setEntity(httpEntity);
        return execute(httpPost);
    }


    private String getAsString(String url, Map<String, String> params) throws IOException, UserCenterException {
        url = url + "?" + toQueryString(sign(url, params));
        HttpGet httpGet = new HttpGet(url);
        return execute(httpGet);
    }

    private Map<String, String> sign(String url, Map<String, String> params) {
        if (params == null) {
            params = new HashMap<String, String>();
        }
        params.put("url", url);
        params.put("secret", UcConfig.getValue("api.secret"));
        String signature = DigestUtils.md5Hex(toQueryString(params));
        params.put("sign", signature);
        params.remove("url");
        params.remove("secret");
        return params;
    }

    private List<NameValuePair> toNvps(Map<String, String> params) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return nvps;
    }


    private String toQueryString(Map<String, String> params) {
        Map<String, String> sortedMap = new TreeMap<String, String>(params);
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return sb.toString();
    }

}
