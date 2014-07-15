package cn.w.im.web.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * Creator: JimmyLin
 * DateTime: 14-4-12 下午3:33
 * Summary: 打印请求参数.
 */
public class ReqInfoInterceptor extends HandlerInterceptorAdapter {
    private static final Log LOG = LogFactory.getLog(ReqInfoInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Enumeration<String> parameterNames = request.getParameterNames();
        StringBuffer sb = new StringBuffer();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String[] values = request.getParameterValues(paramName);
            sb.append("[").append(paramName).append(":").append(values).append("]");
        }

        String params = !StringUtils.isEmpty(sb.toString()) ? sb.insert(0, "\n").toString() : "";
        LOG.debug("start:[" + request.getMethod() + "] [" + request.getRequestURI() + "]" + params);
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
        LOG.debug("end:[" + request.getMethod() + "] [" + request.getRequestURI() + "]");
    }
}
