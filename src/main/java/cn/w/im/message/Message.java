package cn.w.im.message;

/**
 * Creator: JackieHan
 * DateTime: 13-12-17 下午4:35
 */

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Date;

/**
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS,include = JsonTypeInfo.As.PROPERTY,property = "@class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = LoginMessage.class,name="LoginMessage"),
        @JsonSubTypes.Type(value = LoginResponseMessage.class,name="LoginResponseMessage"),
        @JsonSubTypes.Type(value = LogoutMessage.class,name="LogoutMessage"),
        @JsonSubTypes.Type(value = LogoutResponseMessage.class,name="LogoutResponse"),
        @JsonSubTypes.Type(value = NormalMessage.class,name="NormalResponse")
})
public abstract class Message {
    /**
     * 消息类型
     */
    private MessageType messageType;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 接收时间
     */
    private Date receivedTime;

    public Message(MessageType messageType){
        this.messageType=messageType;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(Date receivedTime) {
        this.receivedTime = receivedTime;
    }
}
