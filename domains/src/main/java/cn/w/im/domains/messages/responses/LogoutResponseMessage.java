package cn.w.im.domains.messages.responses;

import cn.w.im.domains.MessageType;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-24 上午11:09.
 * Summary:登出返回消息.
 */
public class LogoutResponseMessage extends ResponseMessage {

    /**
     * 构造函数.
     */
    public LogoutResponseMessage() {
        super(MessageType.LogoutResponse);
    }

    /**
     * 构造函数.
     * @param success 是否成功.
     */
    public LogoutResponseMessage(boolean success) {
        this();
        this.setSuccess(success);
    }
}
