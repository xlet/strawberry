package cn.w.im.web.controllers;

import cn.w.im.domains.basic.Status;
import cn.w.im.web.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * status controller.
 */
@Controller
public class StatusController extends BaseController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/{version:\\d}.{wid:\\d{7,14}}.{param:\\d}")
    public String isOnline(HttpServletRequest request, @PathVariable(value = "version") int version,
                           @PathVariable(value = "wid") String wid,
                           @PathVariable(value = "param") String param) {
        String referrer = this.getReferrer(request);
        Status memberStatus = memberService.getStatus(wid, referrer);
        if (memberStatus.equals(Status.Online)) {
            return "redirect:/client/status/" + version + "/" + param + "/online.gif";
        }else{
            return "redirect:/client/status/" + version + "/" + param + "/offline.gif";
        }
    }
}
