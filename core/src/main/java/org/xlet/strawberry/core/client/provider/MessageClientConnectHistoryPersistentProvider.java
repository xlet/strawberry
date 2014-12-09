package org.xlet.strawberry.core.client.provider;

import org.xlet.strawberry.core.client.MessageClient;
import org.xlet.strawberry.core.client.MessageClientConnectHistory;

/**
 * message client connect history persistent provider.
 */
public interface MessageClientConnectHistoryPersistentProvider {

    /**
     * save history.
     *
     * @param history message client connect history.
     */
    void save(MessageClientConnectHistory history);

    /**
     * get client last connect history.
     *
     * @param client client.
     * @return client last connect history.
     */
    MessageClientConnectHistory last(MessageClient client);
}
