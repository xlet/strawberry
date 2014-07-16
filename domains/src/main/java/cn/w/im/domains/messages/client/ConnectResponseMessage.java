package cn.w.im.domains.messages.client;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.basic.Member;
import cn.w.im.domains.messages.ResponseMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 上午10:04.
 * Summary: 连接应答消息.
 */
public class ConnectResponseMessage extends ResponseMessage implements ServerToClientMessage {

    private Member self;

    private List<Member> nearlyLinkmen = new ArrayList<Member>();

    private List<NormalMessage> offlineMessages = new ArrayList<NormalMessage>();

    /**
     * 构造函数.
     */
    public ConnectResponseMessage() {
        super(MessageType.ConnectResponse);
    }

    /**
     * constructor.
     *
     * @param self                login member self info.
     * @param nearlyLinkmen       nearly linkman list.
     * @param notReceivedMessages not received message.
     */
    public ConnectResponseMessage(Member self, List<Member> nearlyLinkmen, List<NormalMessage> notReceivedMessages) {
        this();
        this.nearlyLinkmen = nearlyLinkmen;
        this.offlineMessages = notReceivedMessages;
    }

    /**
     * 构造函数.
     *
     * @param errorCode    error code.
     * @param errorMessage error message.
     */
    public ConnectResponseMessage(int errorCode, String errorMessage) {
        super(MessageType.ConnectResponse, errorCode, errorMessage);
    }

    /**
     * get nearly linkmen.
     *
     * @return nearly linkmen.
     */
    public List<Member> getNearlyLinkmen() {
        return nearlyLinkmen;
    }

    /**
     * set nearly linkmen.
     *
     * @param nearlyLinkmen nearly linkman list.
     */
    public void setNearlyLinkmen(List<Member> nearlyLinkmen) {
        this.nearlyLinkmen = nearlyLinkmen;
    }

    /**
     * get not received message list.
     *
     * @return not received message list.
     */
    public List<NormalMessage> getOfflineMessages() {
        return offlineMessages;
    }

    /**
     * set not received message list.
     *
     * @param offlineMessages not received message.
     */
    public void setOfflineMessages(List<NormalMessage> offlineMessages) {
        this.offlineMessages = offlineMessages;
    }

    public Member getSelf() {
        return self;
    }

    public void setSelf(Member self) {
        this.self = self;
    }
}
