package cn.w.im.core.message;

import cn.w.im.core.BaseDomain;
import cn.w.im.core.MessageType;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-17 下午4:35.
 * Summary:所有消息的父类.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
public abstract class Message implements BaseDomain {
    /**
     * 消息类型.
     */
    private MessageType messageType;

    /**
     * 发送时间.
     */
    private long sendTime;

    /**
     * 接收时间.
     */
    private long receivedTime;

    /**
     * constructor.
     *
     * @param messageType messageType.
     */
    public Message(MessageType messageType) {
        this.messageType = messageType;
        this.sendTime = System.currentTimeMillis();
    }

    /**
     * 获取消息类型.
     *
     * @return 消息类型.
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * 设置消息类型.
     *
     * @param messageType 消息类型.
     */
    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    /**
     * 获取发送时间.
     *
     * @return 发送时间.
     */
    public long getSendTime() {
        return sendTime;
    }

    /**
     * 设置发送时间.
     *
     * @param sendTime 发送时间.
     */
    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 获取接收时间.
     *
     * @return 接收时间.
     */
    public long getReceivedTime() {
        return receivedTime;
    }

    /**
     * 设置接收时间.
     *
     * @param receivedTime 接收时间.
     */
    public void setReceivedTime(long receivedTime) {
        this.receivedTime = receivedTime;
    }
}
