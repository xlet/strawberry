package cn.w.im.persistent.mongo.domain.message.server;

import cn.w.im.core.client.MessageClientType;
import cn.w.im.core.message.MessageType;
import cn.w.im.core.message.server.MemberLogoutMessage;
import cn.w.im.persistent.mongo.domain.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * mongo member logout message.
 */
@Entity("memberLogoutMessage")
public class MongoMemberLogoutMessage implements MongoDomain {

    @Id
    private ObjectId persistentId;

    private long persistentTime;

    private MessageType messageType;

    private long sendTime;

    private long receivedTime;

    private String memberId;

    private String clientHost;

    private MessageClientType clientType;

    private String serverNodeId;

    public MongoMemberLogoutMessage() {
        this.persistentTime = System.currentTimeMillis();
    }

    public MongoMemberLogoutMessage(MemberLogoutMessage message) {
        this.setClientHost(message.getClientHost());
        this.setClientType(message.getClientType());
        this.setMemberId(message.getMemberId());
        this.setMessageType(message.getMessageType());
        this.setReceivedTime(message.getReceivedTime());
        this.setSendTime(message.getSendTime());
        this.setServerNodeId(message.getServerBasic().getNodeId());
    }


    @Override
    public ObjectId getPersistentId() {
        return this.persistentId;
    }

    @Override
    public void setPersistentId(ObjectId id) {
        this.persistentId = id;
    }

    @Override
    public long getPersistentTime() {
        return this.persistentTime;
    }

    @Override
    public void setPersistentTime(long persistentTime) {
        this.persistentTime = persistentTime;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public long getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(long receivedTime) {
        this.receivedTime = receivedTime;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getClientHost() {
        return clientHost;
    }

    public void setClientHost(String clientHost) {
        this.clientHost = clientHost;
    }

    public MessageClientType getClientType() {
        return clientType;
    }

    public void setClientType(MessageClientType clientType) {
        this.clientType = clientType;
    }

    public String getServerNodeId() {
        return serverNodeId;
    }

    public void setServerNodeId(String serverNodeId) {
        this.serverNodeId = serverNodeId;
    }
}
