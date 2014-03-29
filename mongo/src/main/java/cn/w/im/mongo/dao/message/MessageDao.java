package cn.w.im.mongo.dao.message;

import cn.w.im.domains.messages.Message;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-29 下午7:17.
 * Summary: message dao interface.
 */
public interface MessageDao<TMessage extends Message> {

    /**
     * save message.
     * @param message
     */
    void save(TMessage message);
}
