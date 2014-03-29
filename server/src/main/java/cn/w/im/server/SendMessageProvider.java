package cn.w.im.server;

import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.messages.Message;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-25 下午2:54.
 * Summary: send message provider.
 */
public interface SendMessageProvider {

    /**
     * send message.
     *
     * @param host    host.
     * @param port    port.
     * @param message message.
     */
    void send(String host, int port, Message message);

    /**
     * send to all the specified type of server.
     *
     * @param serverType server type.
     * @param message    message.
     */
    void send(ServerType serverType, Message message);

    /**
     * send to specified server.
     *
     * @param serverBasic server basic.
     * @param message     message.
     */
    void send(ServerBasic serverBasic, Message message);

    /**
     * send to logged with login id message client.
     *
     * @param loginId login id.
     * @param message message.
     */
    void send(String loginId, Message message);
}
