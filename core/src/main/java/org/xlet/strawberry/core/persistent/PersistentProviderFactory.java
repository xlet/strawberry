package org.xlet.strawberry.core.persistent;

import org.xlet.strawberry.core.message.MessageType;
import org.xlet.strawberry.core.config.Configuration;
import org.xlet.strawberry.core.message.Message;
import org.xlet.strawberry.core.message.persistent.MessagePersistentProvider;
import org.xlet.strawberry.core.spring.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * persistent repository factory.
 */
public class PersistentProviderFactory {
    private static final Logger logger = LoggerFactory.getLogger(PersistentProviderFactory.class);

    private static Configuration configuration;

    private static Configuration getConfiguration() {
        if (configuration == null) {
            configuration = SpringContext.context().getBean(Configuration.class);
        }
        return configuration;
    }

    public static <TMessage extends Message> MessagePersistentProvider getMessagePersistentProvider(TMessage message)
            throws NotSupportedDataStoreException, NotSupportMessageTypeException {
        String dataStoreType = getConfiguration().getDataStoreType();
        MessageType messageType = message.getMessageType();
        if (dataStoreType.equals("mongo")) {
            MessagePersistentProvider dao = SpringContext.context().
                    getBean("mongo" + messageType.toString() + "MessagePersistentProvider", MessagePersistentProvider.class);
            if (dao == null) {
                throw new NotSupportMessageTypeException(messageType);
            }
            return dao;
        }
        throw new NotSupportedDataStoreException(dataStoreType);
    }

    public static <TPersistentProvider> TPersistentProvider getPersistentProvider(Class<TPersistentProvider> persistentProviderClass)
            throws NotSupportedDataStoreException {
        String providerClassName = persistentProviderClass.getSimpleName();
        String dataStoreType = getConfiguration().getDataStoreType();
        if (dataStoreType.equals("mongo")) {
            String mongoDaoName = "mongo" + providerClassName;
            TPersistentProvider dao = SpringContext.context().getBean(mongoDaoName, persistentProviderClass);
            if (dao == null) {
                throw new NullPointerException(mongoDaoName + " is not configured.");
            }
            return dao;
        }
        throw new NotSupportedDataStoreException(dataStoreType);
    }
}
