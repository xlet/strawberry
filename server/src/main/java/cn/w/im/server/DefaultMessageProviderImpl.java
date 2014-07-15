package cn.w.im.server;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.conf.Configuration;
import cn.w.im.domains.messages.client.NormalMessage;
import cn.w.im.exceptions.NotSupportMessageTypeException;
import cn.w.im.exceptions.NotSupportedDataStoreException;
import cn.w.im.persistent.MessageDao;
import cn.w.im.persistent.mongo.message.client.MongoNormalMessageDao;
import cn.w.im.utils.spring.SpringContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * default implement {@link cn.w.im.server.MessageProvider}
 */
public class DefaultMessageProviderImpl implements MessageProvider {

    private final static Log LOG = LogFactory.getLog(DefaultMessageProviderImpl.class);

    private Configuration configuration;
    private MongoNormalMessageDao messageDao;


    public DefaultMessageProviderImpl() {

        configuration = SpringContext.context().getBean(Configuration.class);
        if (configuration.getDataStoreType().equals("mongo")) {
            messageDao = (MongoNormalMessageDao) SpringContext.context().getBean("mongo" + MessageType.Normal + "MessageDao");

            if (messageDao == null) {
                LOG.error("default message provider create error! not support normal message!");
            }
        } else {
            LOG.error("default message provider create error! not support data store type:" + configuration.getDataStoreType());
        }
    }

    @Override
    public List<NormalMessage> getNotReceivedMessage(String from, String to) {
        LOG.debug("get not received message by to=" + to);
        List<NormalMessage> messages = messageDao.getNotReceivedMessage(from,to);
        LOG.debug("get " + messages.size() + " messages!");
        return messages;
    }
}
