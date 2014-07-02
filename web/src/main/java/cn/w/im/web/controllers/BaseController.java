package cn.w.im.web.controllers;

import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by jackie on 14-6-23.
 */
public class BaseController {
    /**
     * get cookie.
     * @param request servlet request.
     * @param cookieName cookie name.
     * @return cookie {@link javax.servlet.http.Cookie}
     */
    protected Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie;
            }
        }
        return null;
    }

    /**
     * get client ip.
     * @param request servlet request.
     * @return client ip.
     */
    protected String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * get user agent.
     * @param request servlet request.
     * @return user agent.
     */
    protected String getAgent(HttpServletRequest request){
        String userAgent = request.getHeader("User-Agent");
        return  userAgent;
    }

    /**
     * get Referrer.
     * @param request servlet request.
     * @return Referrer.
     */
    protected String getReferrer(HttpServletRequest request){
        return request.getHeader("Referer");
    }

    /**
     * get host.
     * @param request servlet request.
     * @return host.
     */
    protected String getHost(HttpServletRequest request){
        return request.getHeader("Host");
    }
}
