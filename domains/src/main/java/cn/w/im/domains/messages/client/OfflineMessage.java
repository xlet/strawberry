package cn.w.im.domains.messages.client;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.messages.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * office line message.
 * <p/>
 * when client connected message server,send this message to client.
 */
public class OfflineMessage extends Message implements ServerToClientMessage {

    private List<NormalMessage> offlineMessages = new ArrayList<NormalMessage>();

    public OfflineMessage() {
        super(MessageType.Offline);
    }

    public OfflineMessage(List<NormalMessage> offlineMessages) {
        this();
        this.offlineMessages = offlineMessages;
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
}
