package cn.w.im.web.controllers;

import cn.w.im.web.services.LinkStatusService;
import cn.w.im.web.services.MemberService;
import cn.w.im.web.vo.request.GetTokenRequest;
import cn.w.im.web.vo.response.GetTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by jackie on 14-6-27.
 */
@Controller
@RequestMapping("/token")
public class TokenController extends BaseController {

    private static final String TEMP_COOKIE_ID = "tempId";
    private static final String TEMP_COOKIE_NAME_ID = "tempName";

    @Autowired
    private LinkStatusService linkStatusService;
    @Autowired
    private MemberService memberService;

    /**
     * get token.
     * @param request servlet request.
     * @param tokenRequest token request object {@link cn.w.im.web.vo.request.GetTokenRequest}.
     * @return get token response,see {@link cn.w.im.web.vo.response.GetTokenResponse}.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public GetTokenResponse get(HttpServletRequest request, GetTokenRequest tokenRequest) {
        String realFromId = tokenRequest.getFromId();
        String realFromName = "";
        if (StringUtils.isEmpty(realFromId)) {
            Cookie tempCookie = this.getCookie(request, TEMP_COOKIE_ID);
            if (tempCookie != null) {
                realFromId = tempCookie.getValue();
            }
            Cookie tempNameCookie = this.getCookie(request, TEMP_COOKIE_NAME_ID);
            if (tempNameCookie != null) {
                realFromName = tempNameCookie.getValue();
            }
        }
        GetTokenResponse getTokenResponse = linkStatusService.create(realFromId,tokenRequest.getToId(),this.getIp(request),
                this.getReferrer(request),request.getRequestURI(),this.getAgent(request));
        getTokenResponse.setFrom(memberService.get(realFromId,this.getReferrer(request)));
        return null;
    }
}
