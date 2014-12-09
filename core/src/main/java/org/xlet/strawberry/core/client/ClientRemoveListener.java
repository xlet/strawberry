package org.xlet.strawberry.core.client;

import org.xlet.strawberry.core.exception.ServerInnerException;

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
