package cn.w.im.web.services;

import cn.w.im.web.vo.response.GetTokenResponse;
import org.springframework.stereotype.Service;

/**
 * @author jackie.
 */
public interface LinkStatusService {
    /**
     * create client token.
     *
     * @param fromId     form id.
     * @param toId       to id.
     * @param ip         client ip.
     * @param referrer   referrer url.
     * @param requestURI request url.
     * @param agent      user agent.
     * @return token, see {@link cn.w.im.web.vo.response.GetTokenResponse}.
     */
    GetTokenResponse create(String fromId, String toId, String ip, String referrer, String requestURI, String agent);
}
