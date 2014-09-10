package cn.w.im.web.controllers;

import cn.w.im.web.services.LinkStatusService;
import cn.w.im.web.services.MemberService;
import cn.w.im.web.vo.request.GetTokenRequest;
import cn.w.im.web.vo.response.GetTokenResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jackie on 14-6-27.
 */
@Controller
@RequestMapping("/api/token")
public class TokenController extends BaseController {

    private static final String TEMP_COOKIE_ID = "tempId";
    private static final String TEMP_COOKIE_NAME_ID = "tempName";

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private LinkStatusService linkStatusService;
    @Autowired
    private MemberService memberService;

    /**
     * get token.
     *
     * @param request      servlet request.
     * @param tokenRequest token request object {@link cn.w.im.web.vo.request.GetTokenRequest}.
     * @return get token response,see {@link cn.w.im.web.vo.response.GetTokenResponse}.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public String get(HttpServletResponse response, HttpServletRequest request, GetTokenRequest tokenRequest,
                      @CookieValue(value = TEMP_COOKIE_ID, defaultValue = "") String cookieFromId) throws JsonProcessingException {
        String realFromId = tokenRequest.getFromId();
        if (StringUtils.isEmpty(realFromId)) {
            if (!StringUtils.isEmpty(cookieFromId)) {
                realFromId = cookieFromId;
            }
        }
        GetTokenResponse getTokenResponse = linkStatusService.create(realFromId, tokenRequest.getToId(), this.getIp(request),
                this.getReferrer(request), request.getRequestURI(), this.getAgent(request));
        getTokenResponse.setFrom(memberService.get(realFromId, this.getReferrer(request)));
        if (!StringUtils.isEmpty(tokenRequest.getToId())) {
            getTokenResponse.setTo(memberService.get(tokenRequest.getToId(), this.getReferrer(request)));
        }
        String responseJson = mapper.writeValueAsString(getTokenResponse);
        if (!StringUtils.isEmpty(tokenRequest.getCallback())) {
            responseJson = tokenRequest.getCallback() + "(" + responseJson + ")";
        }
        if (StringUtils.isEmpty(realFromId)) {
            int cookieExpires = 60 * 60 * 24 * 30 * 12;
            Cookie tempIdCookie = new Cookie(TEMP_COOKIE_ID, getTokenResponse.getTempId());
            tempIdCookie.setPath("/");
            tempIdCookie.setMaxAge(cookieExpires);
            tempIdCookie.setDomain(request.getServerName());
            response.addCookie(tempIdCookie);

            Cookie tempNameCookie = new Cookie(TEMP_COOKIE_NAME_ID, getTokenResponse.getTempName());
            tempNameCookie.setPath("/");
            tempNameCookie.setMaxAge(cookieExpires);
            tempNameCookie.setDomain(request.getServerName());
            response.addCookie(tempNameCookie);
        }
        return responseJson;
    }
}
