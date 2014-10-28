package cn.w.im.utils.sdk.oa;

import cn.w.im.utils.sdk.oa.request.GetOrganizationMemberRequest;
import cn.w.im.utils.sdk.oa.request.VerifyRequest;
import cn.w.im.utils.sdk.oa.response.GetOrganizationMemberResponse;
import cn.w.im.utils.sdk.oa.response.VerifyResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * oa member service impl.
 */
public class OaMemberServiceImpl implements OaMemberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OaMemberServiceImpl.class);

    private ObjectMapper mapper = new ObjectMapper();

    private OaConfig config;

    public OaConfig getConfig() {
        return config;
    }

    public void setConfig(OaConfig config) {
        this.config = config;
    }

    @Override
    public VerifyResponse verify(VerifyRequest request) throws Exception {
        String url = config.getBaseUrl() + "liuliuapi?id=" + request.getId() + "&password=" + request.getPassword();
        String responseStr = doPost(url, null);
        return mapper.readValue(responseStr, VerifyResponse.class);
    }

    @Override
    public GetOrganizationMemberResponse getMembers(GetOrganizationMemberRequest request) throws Exception {
        String url = config.getBaseUrl() + "liuliuapi?orgCode=" + request.getOrgCode();
        String responseStr = doGet(url);
        return mapper.readValue(responseStr, GetOrganizationMemberResponse.class);
    }

    private String doGet(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        return execute(httpGet);
    }

    private String doPost(String url, Map<String, String> parameters) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        if (parameters != null) {
            HttpEntity httpEntity = new UrlEncodedFormEntity(convertToNameValuePair(parameters), "utf-8");
            httpPost.setEntity(httpEntity);
        }
        return execute(httpPost);
    }

    private List<NameValuePair> convertToNameValuePair(Map<String, String> params) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return nvps;
    }

    private String execute(HttpUriRequest request) throws Exception {
        LOGGER.debug("request =>" + request.getURI().toString());
        CloseableHttpResponse httpResponse = config.getHttpClient().execute(request);
        int httpStatus = httpResponse.getStatusLine().getStatusCode();
        if (httpStatus == 200) {
            String resp = EntityUtils.toString(httpResponse.getEntity());
            LOGGER.debug("response =>\n" + resp);
            return resp;
        }
        throw new Exception("request error, code[" + httpStatus + "].");
    }
}
