package org.xlet.strawberry.core.message.persistent;

import org.xlet.strawberry.core.message.Message;

/**
 * message persistent provider.
 */
public interface MessagePersistentProvider<TMessage extends Message> {
    /**
     * save message.
     *
     * @param message
     */
    void save(TMessage message);
}
