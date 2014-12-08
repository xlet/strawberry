package cn.w.im.core.client.provider;

import cn.w.im.core.client.MessageClient;
import cn.w.im.core.client.MessageClientConnectHistory;

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
