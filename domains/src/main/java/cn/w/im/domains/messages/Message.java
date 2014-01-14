package cn.w.im.domains.messages;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.messages.responses.LoginResponseMessage;
import cn.w.im.domains.messages.responses.LogoutResponseMessage;
import cn.w.im.domains.messages.responses.MessageServerRegisterResponseMessage;
import cn.w.im.domains.messages.responses.RegisterResponseMessage;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Date;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-17 下午4:35.
 * Summary:所有消息的父类.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = LoginMessage.class, name = "LoginMessage"),
        @JsonSubTypes.Type(value = LoginResponseMessage.class, name = "LoginResponseMessage"),
        @JsonSubTypes.Type(value = LogoutMessage.class, name = "LogoutMessage"),
        @JsonSubTypes.Type(value = LogoutResponseMessage.class, name = "LogoutResponse"),
        @JsonSubTypes.Type(value = NormalMessage.class, name = "NormalResponse"),
        @JsonSubTypes.Type(value = MessageServerRegisterMessage.class, name = "MessageServerRegisterMessage"),
        @JsonSubTypes.Type(value = RegisterResponseMessage.class, name = "RegisterResponseMessage"),
        @JsonSubTypes.Type(value = MessageServerRegisterResponseMessage.class, name = "MessageServerRegisterResponseMessage")
})
public abstract class Message {
    /**
     * 消息类型.
     */
    private MessageType messageType;

    /**
     * 发送时间.
     */
    private Date sendTime;

    /**
     * 接收时间.
     */
    private Date receivedTime;

    /**
     * 构造函数.
     * @param messageType 消息类型.
     */
    public Message(MessageType messageType) {
        this.messageType = messageType;
        this.sendTime = new Date();
    }

    /**
     * 获取消息类型.
     * @return 消息类型.
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * 设置消息类型.
     * @param messageType 消息类型.
     */
    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    /**
     * 获取发送时间.
     * @return 发送时间.
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * 设置发送时间.
     * @param sendTime 发送时间.
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 获取接收时间.
     * @return 接收时间.
     */
    public Date getReceivedTime() {
        return receivedTime;
    }

    /**
     * 设置接收时间.
     * @param receivedTime 接收时间.
     */
    public void setReceivedTime(Date receivedTime) {
        this.receivedTime = receivedTime;
    }
}
