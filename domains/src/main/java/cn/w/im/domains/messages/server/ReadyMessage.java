package cn.w.im.domains.messages.server;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.messages.Message;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-19 下午1:51.
 * Summary: message server ready message.
 * when message server registered to message bus server and got all other started message server linked clients,
 * send this message to login server.
 * means this message server ready to let normal client to link,and forward message.
 */
public class ReadyMessage extends Message implements ServerToServerMessage {

    private ServerBasic messageServer;

    /**
     * constructor.
     */
    public ReadyMessage() {
        super(MessageType.Ready);
    }

    /**
     * constructor.
     *
     * @param messageServer message server basic.
     */
    public ReadyMessage(ServerBasic messageServer) {
        this();
        this.messageServer = messageServer;
    }

    /**
     * get ready message server basic.
     *
     * @return message server basic.
     */
    public ServerBasic getMessageServer() {
        return messageServer;
    }

    /**
     * set ready message server basic.
     *
     * @param messageServer message server basic.
     */
    public void setMessageServer(ServerBasic messageServer) {
        this.messageServer = messageServer;
    }

    @Override
    public ServerBasic getFromServer() {
        return this.messageServer;
    }
}
