package cn.w.im.web.vo.response;

import cn.w.im.web.vo.MessageViewObject;

import java.util.ArrayList;
import java.util.List;

/**
 * get message response.
 */
public class GetMessageResponse extends BaseResponse {

    private List<MessageViewObject> messages =new ArrayList<MessageViewObject>();

    public List<MessageViewObject> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageViewObject> messages) {
        this.messages = messages;
    }
}
