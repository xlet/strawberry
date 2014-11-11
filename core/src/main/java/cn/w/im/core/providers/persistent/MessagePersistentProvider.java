package cn.w.im.core.providers.persistent;

import cn.w.im.core.message.Message;

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
