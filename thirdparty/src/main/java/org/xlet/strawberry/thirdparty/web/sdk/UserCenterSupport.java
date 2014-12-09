package org.xlet.strawberry.thirdparty.web.sdk;

import org.xlet.strawberry.thirdparty.web.sdk.config.UcConfig;
import org.xlet.strawberry.thirdparty.web.sdk.model.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * Creator: JimmyLin
 * DateTime: 14-6-30 上午10:30
 * Summary:
 */
public class UserCenterSupport {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final ObjectMapper mapper = new ObjectMapper();

    protected UcConfig config;


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
        logger.debug("request =>" + request.getURI().toString());
        CloseableHttpResponse httpResponse = config.getHttpClient().execute(request);
        int httpStatus = httpResponse.getStatusLine().getStatusCode();
        if (httpStatus == 200) {
            String resp = EntityUtils.toString(httpResponse.getEntity());
            logger.debug("response =>\n" + resp);
            return resp;
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
        params.put("secret", config.getSecret());
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


    protected void checkWid(String wid) throws UserCenterException {
        if (!StringUtils.isNumeric(wid) || StringUtils.length(wid) < 7 || StringUtils.length(wid) > 14) {
            throw new UserCenterException("[" + wid + "] is not valid");
        }
    }

    public UcConfig getConfig() {
        return config;
    }

    public void setConfig(UcConfig config) {
        this.config = config;
    }
}
