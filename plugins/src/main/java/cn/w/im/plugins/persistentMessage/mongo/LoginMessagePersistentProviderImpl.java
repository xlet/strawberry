package cn.w.im.plugins.persistentMessage.mongo;

import cn.w.im.domains.messages.LoginMessage;
import cn.w.im.domains.messages.Message;
import cn.w.im.domains.mongo.MongoLoginMessage;
import cn.w.im.mongo.dao.message.MongoLoginMessageDao;
import cn.w.im.plugins.persistentMessage.MessagePersistentProvider;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-6 上午10:48.
 * Summary: 登陆消息序列化处理实现.
 */
public class LoginMessagePersistentProviderImpl implements MessagePersistentProvider {

    /**
     * MongoLoginMessageDao.
     */
    private MongoLoginMessageDao dao;

    /**
     * 构造函数.
     * @param dao mongoLoginMessageDao.
     */
    public LoginMessagePersistentProviderImpl(MongoLoginMessageDao dao) {
        this.dao = dao;
    }

    @Override
    public void save(Message message) {
        LoginMessage loginMessage = (LoginMessage) message;
        MongoLoginMessage mongoLoginMessage = new MongoLoginMessage(loginMessage);
        dao.save(mongoLoginMessage);
    }
}
