package cn.w.im.domains.messages.responses;

import cn.w.im.domains.messages.Message;
import cn.w.im.domains.MessageType;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-10 下午2:40.
 * Summary: 回复消息.
 */
public abstract class ResponseMessage extends Message {

    private boolean success;

    private String errorMessage;

    /**
     * 构造函数.
     *
     * @param messageType 消息类型.
     */
    public ResponseMessage(MessageType messageType) {
        super(messageType);
    }

    /**
     * 获取是否成功.
     * @return true:成功.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * 设置是否成功.
     * @param success true:成功.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
