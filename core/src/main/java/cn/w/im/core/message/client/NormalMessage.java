package cn.w.im.core.message.client;

import cn.w.im.core.MessageType;
import cn.w.im.core.MessageClientType;
import cn.w.im.core.message.Message;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-24 下午2:21.
 * Summary:一般消息.
 */
public class NormalMessage extends Message implements ServerToClientMessage, ClientToServerMessage {

    private String from, to, content;
    private boolean forward;
    private MessageClientType clientType;

    /**
     * 构造函数.
     */
    public NormalMessage() {
        super(MessageType.Normal);
    }

    /**
     * 构造函数.
     *
     * @param messageClientType message client type.
     * @param from              发送方id.
     * @param to                接收方id.
     * @param content           消息内容.
     */
    public NormalMessage(MessageClientType messageClientType, String from, String to, String content) {
        this();
        this.clientType = messageClientType;
        this.from = from;
        this.to = to;
        this.content = content;
    }

    /**
     * get message client type.
     *
     * @return message client type.
     */
    public MessageClientType getClientType() {
        return this.clientType;
    }

    /**
     * set message client type.
     *
     * @param clientType message client type.
     */
    public void setClientType(MessageClientType clientType) {
        this.clientType = clientType;
    }

    /**
     * 获取发送方id.
     *
     * @return 发送方id.
     */
    public String getFrom() {
        return from;
    }

    /**
     * 设置发送方id.
     *
     * @param from 发送方id.
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * 获取接收方id.
     *
     * @return 接收方id.
     */
    public String getTo() {
        return to;
    }

    /**
     * 设置接收方id.
     *
     * @param to 接收方id.
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * 获取消息内容.
     *
     * @return 消息内容.
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置消息内容.
     *
     * @param content 消息内容.
     */
    public void setContent(String content) {
        this.content = content;
    }

    @JsonIgnore
    public boolean isForward() {
        return this.forward;
    }

    public void setForward(boolean forward) {
        this.forward = forward;
    }
}
