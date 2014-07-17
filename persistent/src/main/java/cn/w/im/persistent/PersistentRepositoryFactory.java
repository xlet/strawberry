package cn.w.im.persistent;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.conf.Configuration;
import cn.w.im.domains.messages.Message;
import cn.w.im.exceptions.NotSupportMessageTypeException;
import cn.w.im.exceptions.NotSupportedDataStoreException;
import cn.w.im.utils.spring.SpringContext;
import org.apache.log4j.Logger;

/**
 * persistent repository factory.
 */
public class PersistentRepositoryFactory {
    private static final Logger logger = Logger.getLogger(PersistentRepositoryFactory.class);

    private static Configuration configuration;

    private static Configuration getConfiguration() {
        if (configuration == null) {
            configuration = SpringContext.context().getBean(Configuration.class);
        }
        return configuration;
    }

    public static <TMessage extends Message> MessageDao getMessageDao(TMessage message)
            throws NotSupportedDataStoreException, NotSupportMessageTypeException {

        String dataStoreType = getConfiguration().getDataStoreType();
        MessageType messageType = message.getMessageType();
        if (dataStoreType.equals("mongo")) {
            MessageDao dao = (MessageDao) SpringContext.context().getBean("mongo" + messageType.toString() + "MessageDao");
            if (dao == null) {
                throw new NotSupportMessageTypeException(messageType);
            }
            return dao;
        }
        throw new NotSupportedDataStoreException(dataStoreType);
    }

    public static <TDao> TDao getDao(Class<TDao> DAOClass) throws NotSupportedDataStoreException {
        String daoClassName = DAOClass.getSimpleName();
        String dataStoreType = getConfiguration().getDataStoreType();
        if (dataStoreType.equals("mongo")) {
            String mongoDaoName = "mongo" + daoClassName;
            //
            logger.debug("mongoDaoName="+mongoDaoName);
            TDao dao = (TDao) SpringContext.context().getBean(mongoDaoName);
            if (dao == null) {
                throw new NullPointerException(mongoDaoName + " is not configured.");
            }
        }
        throw new NotSupportedDataStoreException(dataStoreType);
    }
}
