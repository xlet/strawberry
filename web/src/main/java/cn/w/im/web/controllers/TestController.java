package cn.w.im.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author jackie.
 */
@Controller
@RequestMapping("/test")
public class TestController {

    /**
     * test get request header.
     *
     * @param request servlet request.
     * @return request header string.
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String test(HttpServletRequest request) {
        String header = "";
        Enumeration<String> attrNames = request.getHeaderNames();
        while (attrNames.hasMoreElements()) {
            String attrName = attrNames.nextElement();
            Object attrValue = request.getHeader(attrName);
            header += attrName + "[" + attrValue + "]<br /><br />";
        }
        return header;
    }


    @RequestMapping(value = "/{wid}")
    public String toChat(@PathVariable(value = "wid")String wid, Model model){
        model.addAttribute("wid", wid);
        return "chat";
    }

}
