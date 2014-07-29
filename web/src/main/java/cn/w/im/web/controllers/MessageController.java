package cn.w.im.web.controllers;

import cn.w.im.domains.client.MessageClientType;
import cn.w.im.domains.messages.client.NormalMessage;
import cn.w.im.web.services.MemberService;
import cn.w.im.web.services.MessageService;
import cn.w.im.web.vo.MemberViewObject;
import cn.w.im.web.vo.MessageViewObject;
import cn.w.im.web.vo.response.GetMessageResponse;
import cn.w.im.web.vo.response.SendMessageResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * message controller.
 */
@Controller
@RequestMapping(value = "/api/message")
public class MessageController extends BaseController {

    private static final Logger logger = Logger.getLogger(MessageController.class);

    private final static int MAX_MESSAGE_LENGTH = 255;

    private final static SimpleDateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MessageService messageService;
    @Autowired
    private MemberService memberService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String get(HttpServletRequest request, String token, String fromId,
                      @RequestParam(value = "toId", defaultValue = "") String toId,
                      @RequestParam(value = "content", defaultValue = "") String content,
                      @RequestParam(value = "callback", defaultValue = "") String callback) throws JsonProcessingException {
        String referrer = this.getReferrer(request);
        if (StringUtils.isEmpty(content)) {
            return getMessage(token, fromId, toId, referrer, callback);
        } else {
            return sendMessage(token, fromId, toId, content, referrer, callback);
        }
    }

    private String getMessage(String token, String loginId, String toId, String referrer, String callback) throws JsonProcessingException {
        List<NormalMessage> notReceivedMessages = messageService.getNotReceivedMessage(loginId, toId);
        List<MessageViewObject> messages = createMessageList(null, notReceivedMessages, referrer);
        GetMessageResponse response = new GetMessageResponse();
        response.setSuccess(true);
        response.setToken(token);
        response.setMessages(messages);

        String responseJson = mapper.writeValueAsString(response);
        logger.debug("get:\n"+responseJson);
        if (!StringUtils.isEmpty(callback)) {
            responseJson = callback + "(" + responseJson + ")";
        }
        return responseJson;
    }

    private String sendMessage(String token, String fromId, String toId, String content, String referrer, String callback) throws JsonProcessingException {
        if (StringUtils.isEmpty(content)) {
            SendMessageResponse response = new SendMessageResponse();
            response.setSuccess(false);
            response.setErrorCode(101);
            response.setErrorMessage("消息内容为空!");
            response.setToken(token);
            return createSendResponse(response, callback);
        }

        if (content.length() > MAX_MESSAGE_LENGTH) {
            SendMessageResponse response = new SendMessageResponse();
            response.setSuccess(false);
            response.setErrorCode(102);
            response.setErrorMessage("消息内容太长!");
            response.setToken(token);
        }

        NormalMessage message = new NormalMessage(MessageClientType.Web, fromId, toId, content);
        List<NormalMessage> notReceivedMessages = messageService.sendMessage(message);

        SendMessageResponse response = new SendMessageResponse();
        response.setSuccess(true);
        response.setToken(token);
        response.setToId(toId);
        MemberViewObject toVo = memberService.get(toId, referrer);
        response.setToName(toVo.getNickName());
        response.setToStatus(toVo.getStatus());
        response.setMessages(createMessageList(message, notReceivedMessages, referrer));

        return createSendResponse(response, callback);
    }

    private List<MessageViewObject> createMessageList(NormalMessage message, List<NormalMessage> messages, String referrer) {
        List<MessageViewObject> messageViewObjects = new ArrayList<MessageViewObject>();
        if (message != null) {
            MessageViewObject self = new MessageViewObject();
            self.setContent(message.getContent());
            self.setToId(message.getTo());
            self.setTo(memberService.get(message.getTo(), referrer).getNickName());
            self.setFromId(message.getFrom());
            self.setFrom(memberService.get(message.getFrom(), referrer).getNickName());
            self.setSendTime(fullDateFormat.format(new Date(message.getSendTime())));

            messageViewObjects.add(self);
        }

        for (NormalMessage normalMessage : messages) {
            MessageViewObject messageViewObject = new MessageViewObject();
            messageViewObject.setContent(normalMessage.getContent());
            messageViewObject.setToId(normalMessage.getTo());
            messageViewObject.setTo(memberService.get(normalMessage.getTo(), referrer).getNickName());
            messageViewObject.setFromId(normalMessage.getTo());
            messageViewObject.setFrom(memberService.get(normalMessage.getFrom(), referrer).getNickName());
            messageViewObject.setSendTime(fullDateFormat.format(new Date(normalMessage.getSendTime())));
            messageViewObjects.add(messageViewObject);
        }

        return messageViewObjects;
    }


    private String createSendResponse(SendMessageResponse sendMessageResponse, String callback) throws JsonProcessingException {

        String responseJson = mapper.writeValueAsString(sendMessageResponse);
        logger.debug("responseJson:\n"+responseJson);
        if (!StringUtils.isEmpty(callback)) {
            responseJson = callback + "(" + responseJson + ")";
        }
        return responseJson;
    }
}
