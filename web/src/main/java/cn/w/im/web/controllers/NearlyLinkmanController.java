package cn.w.im.web.controllers;

import cn.w.im.web.services.NearlyLinkmanService;
import cn.w.im.web.vo.LinkmanViewObject;
import cn.w.im.web.vo.response.GetNearlyLinkmanResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

/**
 * nearly linkman controller.
 */
@Controller
@RequestMapping("/api/nearlyLinkman")
public class NearlyLinkmanController extends BaseController {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private NearlyLinkmanService nearlyLinkmanService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String get(HttpServletRequest request, String token, String fromId,
                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                      @RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
                      @RequestParam(value = "callback", defaultValue = "") String callback) throws JsonProcessingException {
        String referrer = this.getReferrer(request);
        //todo:jackie get nearly linkman.
        List<LinkmanViewObject> linkmanViewObjects = nearlyLinkmanService.get(fromId, referrer, pageSize, pageIndex);
        GetNearlyLinkmanResponse response = new GetNearlyLinkmanResponse();
        response.setSuccess(true);
        response.setToken(token);
        response.setLinkmans(linkmanViewObjects);

        String responseJson = mapper.writeValueAsString(response);
        if (!StringUtils.isEmpty(callback)) {
            responseJson = callback + "(" + responseJson + ")";
        }
        return responseJson;
    }
}
