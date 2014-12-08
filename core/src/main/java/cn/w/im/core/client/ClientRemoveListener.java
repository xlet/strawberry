package cn.w.im.core.client;

import cn.w.im.core.exception.ServerInnerException;

/**
 * client remove listener interface.
 */
public interface ClientRemoveListener {

    /**
     * invoked when client removed.
     *
     * @param client will remove client.
     */
    void onClientRemove(Client client) throws ServerInnerException;
}
