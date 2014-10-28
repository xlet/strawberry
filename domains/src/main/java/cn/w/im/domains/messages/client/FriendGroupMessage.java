package cn.w.im.domains.messages.client;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.messages.Message;
import cn.w.im.domains.relation.FriendGroup;

/**
 * friend group message.
 * <p/>
 * while client linked in message,send this message to client.
 */
public class FriendGroupMessage extends Message implements ServerToClientMessage {

    private FriendGroup friendGroup;

    /**
     * default constructor.
     */
    public FriendGroupMessage() {
        super(MessageType.FriendGroup);
    }

    /**
     * constructor.
     *
     * @param friendGroup friend group.
     */
    public FriendGroupMessage(FriendGroup friendGroup) {
        this();
        this.friendGroup = friendGroup;
    }

    /**
     * get friend group.
     *
     * @return friend group.
     */
    public FriendGroup getFriendGroup() {
        return friendGroup;
    }

    /**
     * set friend group.
     *
     * @param friendGroup friend group.
     */
    public void setFriendGroup(FriendGroup friendGroup) {
        this.friendGroup = friendGroup;
    }
}
