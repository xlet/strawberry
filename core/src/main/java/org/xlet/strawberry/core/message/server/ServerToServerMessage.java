package org.xlet.strawberry.core.message.server;

import org.xlet.strawberry.core.server.ServerBasic;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-26 上午11:44.
 * Summary: server send to server message.
 */
public interface ServerToServerMessage {

    /**
     * get from server basic.
     *
     * @return from server basic.
     */
    ServerBasic getFromServer();
}
