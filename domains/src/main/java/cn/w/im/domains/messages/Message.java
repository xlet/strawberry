package cn.w.im.domains.messages;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.messages.client.*;
import cn.w.im.domains.messages.forward.*;
import cn.w.im.domains.messages.server.*;
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
        //client message
        @JsonSubTypes.Type(value = ConnectMessage.class, name = "Connect"),
        @JsonSubTypes.Type(value = ConnectResponseMessage.class, name = "ConnectResponse"),
        @JsonSubTypes.Type(value = LoginMessage.class, name = "Login"),
        @JsonSubTypes.Type(value = LoginResponseMessage.class, name = "LoginResponse"),
        @JsonSubTypes.Type(value = LogoutMessage.class, name = "Logout"),
        @JsonSubTypes.Type(value = LogoutResponseMessage.class, name = "LogoutResponse"),
        @JsonSubTypes.Type(value = NormalMessage.class, name = "Normal"),
        //forward
        @JsonSubTypes.Type(value = ForwardReadyMessage.class, name = "ForwardReady"),
        @JsonSubTypes.Type(value = ForwardRequestMessage.class, name = "ForwardRequest"),
        @JsonSubTypes.Type(value = ForwardResponseMessage.class, name = "ForwardResponse"),
        //server
        @JsonSubTypes.Type(value = ConnectedMessage.class, name = "Connected"),
        @JsonSubTypes.Type(value = ConnectedResponseMessage.class, name = "ConnectedResponse"),
        @JsonSubTypes.Type(value = ForwardMessage.class, name = "Forward"),
        @JsonSubTypes.Type(value = ReadyMessage.class, name = "Ready"),
        @JsonSubTypes.Type(value = RequestLinkedClientsMessage.class, name = "RequestLinkedClients"),
        @JsonSubTypes.Type(value = ResponseLinkedClientsMessage.class, name = "ResponseLinkedClients"),
        @JsonSubTypes.Type(value = ServerRegisterMessage.class, name = "ServerRegister"),
        @JsonSubTypes.Type(value = ServerRegisterResponseMessage.class, name = "ServerRegisterResponse"),
        @JsonSubTypes.Type(value = TokenMessage.class, name = "Token"),
        @JsonSubTypes.Type(value = TokenResponseMessage.class, name = "TokenResponse")
})
public abstract class Message {
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
        this.sendTime = new Date().getTime();
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
