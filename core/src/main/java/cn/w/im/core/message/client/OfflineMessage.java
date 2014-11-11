package cn.w.im.core.message.client;

import cn.w.im.core.MessageType;
import cn.w.im.core.message.Message;

import java.util.ArrayList;
import java.util.Collection;

/**
 * office line message.
 * <p/>
 * when client connected message server,send this message to client.
 */
public class OfflineMessage extends Message implements ServerToClientMessage {

    private Collection<NormalMessage> offlineMessages = new ArrayList<NormalMessage>();

    public OfflineMessage() {
        super(MessageType.Offline);
    }

    public OfflineMessage(Collection<NormalMessage> offlineMessages) {
        this();
        this.offlineMessages = offlineMessages;
    }

    /**
     * get not received message list.
     *
     * @return not received message list.
     */
    public Collection<NormalMessage> getOfflineMessages() {
        return offlineMessages;
    }

    /**
     * set not received message list.
     *
     * @param offlineMessages not received message.
     */
    public void setOfflineMessages(Collection<NormalMessage> offlineMessages) {
        this.offlineMessages = offlineMessages;
    }
}
