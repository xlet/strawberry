package cn.w.im.plugins.mongo.message.processer;

import cn.w.im.domains.messages.LoginMessage;
import cn.w.im.domains.messages.LogoutMessage;
import cn.w.im.domains.messages.Message;
import cn.w.im.domains.messages.NormalMessage;
import cn.w.im.exceptions.NotSupportMessageTypeException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-6 上午10:41.
 * Summary: mongo 消息序列化处理程序创建工厂.
 */
public class ProcesserFactory {

    /**
     * 创建处理程序.
     *
     * @param message 消息.
     * @return 处理程序实例.
     */
    public static ProcessProvider createProcesser(Message message) {

        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:/applicationContext.xml");

        if (message instanceof LoginMessage) {
            return (MongoLoginMessageProviderImpl) context.getBean("mongoLoginMessageSerializer");
        } else if (message instanceof LogoutMessage) {
            return (MongoLogoutMessageProviderImpl) context.getBean("mongoLogoutMessageSerializer");
        } else if (message instanceof NormalMessage) {
            return (MongoNormalMessageProviderImpl) context.getBean("mongoNormalMessageSerializer");
        } else {
            throw new NotSupportMessageTypeException(message);
        }
    }
}
