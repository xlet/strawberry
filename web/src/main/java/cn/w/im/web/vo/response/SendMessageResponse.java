package cn.w.im.web.vo.response;

import cn.w.im.web.vo.MessageViewObject;

import java.util.ArrayList;
import java.util.List;

/**
 * send message response.
 */
public class SendMessageResponse extends BaseResponse {
    private String toId, toName;
    private int toStatus;
    private List<MessageViewObject> messages = new ArrayList<MessageViewObject>();

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public int getToStatus() {
        return toStatus;
    }

    public void setToStatus(int toStatus) {
        this.toStatus = toStatus;
    }

    public List<MessageViewObject> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageViewObject> messages) {
        this.messages = messages;
    }
}
