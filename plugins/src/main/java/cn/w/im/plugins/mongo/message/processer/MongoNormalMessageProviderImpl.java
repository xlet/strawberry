package cn.w.im.plugins.mongo.message.processer;

import cn.w.im.domains.messages.Message;
import cn.w.im.domains.messages.NormalMessage;
import cn.w.im.domains.mongo.MongoNormalMessage;
import cn.w.im.plugins.mongo.message.dao.MongoNormalMessageDao;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-6 下午3:03.
 * Summary: MongoNoramlMessage 序列化实现.
 */
public class MongoNormalMessageProviderImpl implements ProcessProvider {

    /**
     * MongoNormalMessageDao.
     */
    private MongoNormalMessageDao dao;

    /**
     * 构造函数.
     * @param dao MongoNormalMessageDao.
     */
    public MongoNormalMessageProviderImpl(MongoNormalMessageDao dao) {
        this.dao = dao;
    }

    @Override
    public void serialize(Message message) {
        NormalMessage normalMessage = (NormalMessage) message;
        MongoNormalMessage mongoNormalMessage = new MongoNormalMessage(normalMessage);
        dao.save(mongoNormalMessage);
    }
}
