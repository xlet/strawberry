package org.xlet.strawberry.core.client;

import org.xlet.strawberry.core.client.provider.MessageClientConnectHistoryPersistentProvider;
import org.xlet.strawberry.core.persistent.NotSupportedDataStoreException;
import org.xlet.strawberry.core.persistent.PersistentProviderFactory;

/**
 * client history.
 */
public class MessageClientConnectHistory {

    private MessageClient client;
    private long connectTime;

    public MessageClientConnectHistory(MessageClient client) {
        this.client = client;
        this.connectTime = System.currentTimeMillis();
    }

    public MessageClient getClient() {
        return client;
    }

    public long getConnectTime() {
        return connectTime;
    }

    public void Save() throws NotSupportedDataStoreException {

        this.getPersistentProvider().save(this);
    }

    public static MessageClientConnectHistory last(MessageClient client) throws NotSupportedDataStoreException {
        return getPersistentProvider().last(client);
    }

    private static MessageClientConnectHistoryPersistentProvider getPersistentProvider() throws NotSupportedDataStoreException {
        return PersistentProviderFactory.getPersistentProvider(MessageClientConnectHistoryPersistentProvider.class);
    }
}
