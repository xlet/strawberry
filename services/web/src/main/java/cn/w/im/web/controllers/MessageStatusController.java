package cn.w.im.web.controllers;

import cn.w.im.domains.messages.client.NormalMessage;
import cn.w.im.web.services.MemberService;
import cn.w.im.web.services.MessageService;
import cn.w.im.web.vo.MemberViewObject;
import cn.w.im.web.vo.MessageStatusViewObject;
import cn.w.im.web.vo.response.GetMessageStatusResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * message status controller.
 */
@Controller
@RequestMapping(value = "/api/messageStatus")
public class MessageStatusController extends BaseController {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MessageService messageService;

    @Autowired
    private MemberService memberService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String get(HttpServletRequest request, String token, String fromId,
                      @RequestParam(value = "callback", defaultValue = "") String callback) throws JsonProcessingException {

        String referrer = this.getReferrer(request);

        List<MessageStatusViewObject> messageStatuses = this.get(fromId, referrer);

        GetMessageStatusResponse response = new GetMessageStatusResponse();
        response.setToken(token);
        response.setSuccess(false);
        response.setStatuses(messageStatuses);

        String responseJson = mapper.writeValueAsString(response);
        if (!StringUtils.isEmpty(callback)) {
            responseJson = callback + "(" + responseJson + ")";
        }
        return responseJson;
    }

    private List<MessageStatusViewObject> get(String loginId, String referrer) {
        List<NormalMessage> notReceivedMessages = messageService.getNotReceivedMessage(loginId);
        Map<String, MessageStatusViewObject> messageStatusMap = new HashMap<String, MessageStatusViewObject>();
        for (NormalMessage notReceivedMessage : notReceivedMessages) {
            String key = notReceivedMessage.getFrom() + referrer;
            if (messageStatusMap.containsKey(key)) {
                MessageStatusViewObject messageStatus = messageStatusMap.get(key);
                messageStatus.setCount(messageStatus.getCount() + 1);
            } else {
                MemberViewObject fromVo = memberService.get(notReceivedMessage.getFrom(), referrer);
                MessageStatusViewObject messageStatus = new MessageStatusViewObject();
                messageStatus.setCount(1);
                messageStatus.setStatus(fromVo.getStatus());
                messageStatus.setThumb(fromVo.getThumb());
                messageStatus.setFromId(fromVo.getId());
                messageStatus.setFromName(fromVo.getNickName());
                messageStatusMap.put(key, messageStatus);
            }
        }
        return CollectionUtils.arrayToList(messageStatusMap.values().toArray());
    }
}
