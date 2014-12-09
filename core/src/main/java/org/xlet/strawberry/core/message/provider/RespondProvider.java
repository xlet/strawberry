package org.xlet.strawberry.core.message.provider;

import org.xlet.strawberry.core.server.ServerBasic;
import org.xlet.strawberry.core.message.RespondMessage;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-25 上午10:07.
 * Summary: a message send to server/servers then wait the server/servers response.
 * this provider provide the response message has all received.
 */
public interface RespondProvider {

    /**
     * check all allResponded server respond the response message.
     *
     * @param respondKey the waiting for reply message flag.
     * @return true:all allResponded.
     * @throws NotRegisterRespondMessageException message dit not register exception.
     */
    boolean allResponded(String respondKey) throws NotRegisterRespondMessageException;

    /**
     * received response message.
     *
     * @param responseMessage response message.
     * @throws NotRegisterRespondMessageException message dit not register exception.
     * @throws NotRegisterRespondServerException  server dit not register exception.
     */
    void receivedRespondedMessage(RespondMessage responseMessage) throws NotRegisterRespondMessageException, NotRegisterRespondServerException;

    /**
     * register a message must waiting the server reply.
     *
     * @param respondKey  message flag.
     * @param serverBasic waiting server.
     * @throws RegisteredRespondMessageException message has registered exception.
     * @throws RegisteredRespondServerException  server has registered exception.
     */
    void registerResponded(String respondKey, ServerBasic serverBasic) throws RegisteredRespondMessageException, RegisteredRespondServerException;

    /**
     * interrupt waiting for reply.
     *
     * @param respondKey message respond key.
     * @throws RespondInterruptException respond interrupt exception.
     * @throws NotRegisterRespondMessageException                       message dit not register exception.
     */
    void interrupt(String respondKey) throws RespondInterruptException, NotRegisterRespondMessageException;
}
