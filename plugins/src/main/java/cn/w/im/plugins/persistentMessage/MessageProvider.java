package cn.w.im.plugins.persistentMessage;

import cn.w.im.domains.messages.Message;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-6 上午10:43.
 * Summary: Mongo 消息序列化处理接口.
 */
public interface MessageProvider {
    /**
     * 序列化(缓存)消息.
     * @param message 消息.
     */
    void serialize(Message message);
}
