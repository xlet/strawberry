package cn.w.im.exceptions;

import cn.w.im.domains.messages.Message;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-6 上午11:01.
 * Summary: 不支持的消息类型异常.
 */
public class NotSupportMessageTypeException extends Exception {

    /**
     * 消息.
     */
    private Message msg;

    /**
     * 获取消息.
     *
     * @return 消息.
     */
    public Message getMsg() {
        return msg;
    }

    /**
     * 设置消息.
     *
     * @param msg 消息.
     */
    public void setMsg(Message msg) {
        this.msg = msg;
    }

    /**
     * 构造函数.
     *
     * @param msg 消息.
     */
    public NotSupportMessageTypeException(Message msg) {
        super("not support messageType");
        this.msg = msg;
    }
}
