package cn.w.im.core.providers.client;

/**
 * client remove listener interface.
 */
public interface ClientRemoveListener {

    /**
     * invoked when client removed.
     *
     * @param client will remove client.
     */
    void onClientRemove(Client client);
}
