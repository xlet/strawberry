package cn.w.im.plugins.persistentMessage;

import cn.w.im.domains.messages.LoginMessage;
import cn.w.im.domains.messages.LogoutMessage;
import cn.w.im.domains.messages.NormalMessage;
import cn.w.im.domains.messages.MessageServerRegisterMessage;
import cn.w.im.domains.messages.Message;
import cn.w.im.exceptions.NotSupportMessageTypeException;
import cn.w.im.utils.spring.SpringContext;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-6 上午10:41.
 * Summary: mongo 消息序列化处理程序创建工厂.
 */
public class MessageProviderFactory {

    /**
     * 创建处理程序.
     *
     * @param message 消息.
     * @return 处理程序实例.
     */
    public static MessageProvider createProvider(Message message) {
        if (message instanceof LoginMessage) {
            return (MessageProvider) SpringContext.context().getBean("mongoLoginMessageProviderImpl");
        } else if (message instanceof LogoutMessage) {
            return (MessageProvider) SpringContext.context().getBean("mongoLogoutMessageProviderImpl");
        } else if (message instanceof NormalMessage) {
            return (MessageProvider) SpringContext.context().getBean("mongoNormalMessageProviderImpl");
        } else if (message instanceof MessageServerRegisterMessage) {
            return (MessageProvider) SpringContext.context().getBean("mongoMessageServerRegisterMessageProviderImpl");
        } else {
            throw new NotSupportMessageTypeException(message);
        }
    }
}
