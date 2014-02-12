package cn.w.im.domains.messages;

import cn.w.im.domains.MessageType;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-24 下午2:21.
 * Summary:一般消息.
 */
public class NormalMessage extends Message {

    /**
     * from:发送方id.
     * to:接收方id.
     * content:消息内容.
     */
    private String from, to, content;

    /**
     * 是否转发.
     */
    private boolean forward;

    /**
     * 构造函数.
     */
    public NormalMessage() {
        super(MessageType.Normal);
    }

    /**
     * 构造函数.
     * @param from 发送方id.
     * @param to 接收方id.
     * @param content 消息内容.
     */
    public NormalMessage(String from, String to, String content) {
        this();
        this.from = from;
        this.to = to;
        this.content = content;
    }

    /**
     * 获取发送方id.
     * @return 发送方id.
     */
    public String getFrom() {
        return from;
    }

    /**
     * 设置发送方id.
     * @param from 发送方id.
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * 获取接收方id.
     * @return 接收方id.
     */
    public String getTo() {
        return to;
    }

    /**
     * 设置接收方id.
     * @param to 接收方id.
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * 获取消息内容.
     * @return 消息内容.
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置消息内容.
     * @param content 消息内容.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 是否已转发.
     * @return 转发：true 未转发：false.
     */
    public boolean isForward() {
        return forward;
    }

    /**
     * 设置是否转发.
     * @param forward 转发:true 未转发:false.
     */
    public void setForward(boolean forward) {
        this.forward = forward;
    }
}
