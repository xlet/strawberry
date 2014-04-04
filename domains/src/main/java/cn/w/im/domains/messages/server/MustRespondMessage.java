package cn.w.im.domains.messages.server;

/**
 * Creator: JackieHan.
 * DateTime: 14-4-3 下午5:33.
 * Summary: the message must respond.
 */
public interface MustRespondMessage extends ServerToServerMessage {
    /**
     * get respond key.
     * @return respond key.
     */
    String getRespondKey();
}
