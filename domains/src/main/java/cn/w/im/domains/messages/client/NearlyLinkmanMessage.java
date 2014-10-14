package cn.w.im.domains.messages.client;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.basic.Member;
import cn.w.im.domains.messages.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * nearlyLinkman response message.
 * <p/>
 * when client connected message server,send this message to client.
 */
public class NearlyLinkmanMessage extends Message implements ServerToClientMessage {

    private List<Member> nearlyLinkmen = new ArrayList<Member>();

    public NearlyLinkmanMessage() {
        super(MessageType.NearlyLinkman);
    }

    public NearlyLinkmanMessage(List<Member> nearlyLinkmen) {
        this();
        this.nearlyLinkmen = nearlyLinkmen;
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

}
