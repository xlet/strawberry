package cn.w.im.core.message.server;

import cn.w.im.core.server.ServerBasic;

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
