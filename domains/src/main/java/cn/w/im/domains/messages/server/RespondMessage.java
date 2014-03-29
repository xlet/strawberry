package cn.w.im.domains.messages.server;


/**
 * Creator: JackieHan.
 * DateTime: 14-3-25 上午11:10.
 * Summary: the message must respond or message is a response message.
 */
public interface RespondMessage extends ServerToServerMessage {

    /**
     * get respond key.
     * @return respond key.
     */
    String getRespondKey();
}
