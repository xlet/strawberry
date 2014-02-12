package cn.w.im.plugins.persistentMessage;

import cn.w.im.domains.messages.Message;
import cn.w.im.exceptions.NotSupportMessageTypeException;
import cn.w.im.utils.StringExtension;
import cn.w.im.utils.spring.SpringContext;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-6 上午10:41.
 * Summary: mongo 消息序列化处理程序创建工厂.
 */
public class MessagePersistentProviderFactory {

    /**
     * 创建处理程序.
     *
     * @param message 消息.
     * @return 处理程序实例.
     * @throws NotSupportMessageTypeException 不支持的消息类型.
     */
    public static MessagePersistentProvider createProvider(Message message) throws NotSupportMessageTypeException {
        String messageTypeKey = StringExtension.toLowerCaseFistOne(message.getMessageType().toString());
        String key = messageTypeKey + "MessageProvider";
        MessagePersistentProvider provider = (MessagePersistentProvider) SpringContext.context().getBean(key);
        if (provider != null) {
            return provider;
        } else {
            throw new NotSupportMessageTypeException(message);
        }
    }
}
