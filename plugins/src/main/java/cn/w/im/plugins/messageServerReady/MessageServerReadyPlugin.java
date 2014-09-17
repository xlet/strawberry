package cn.w.im.plugins.messageServerReady;

import cn.w.im.core.plugins.PluginContext;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.messages.server.ReadyMessage;
import cn.w.im.exceptions.ServerInnerException;
import cn.w.im.core.server.LoginServer;
import cn.w.im.domains.ServerType;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.core.plugins.MessagePlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-19 下午4:24.
 * Summary: process message server ready message.
 * only allowed to added to login server.
 * login server received message server ready message that means login server can allocation normal client to this message server.
 */
public class MessageServerReadyPlugin extends MessagePlugin<ReadyMessage> {

    private Logger logger;

    /**
     * 构造函数.
     */
    public MessageServerReadyPlugin() {
        super("MessageServerReadyPlugin", "message core ready.");
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public boolean isMatch(PluginContext context) {
        return (context.getMessage().getMessageType() == MessageType.Ready)
                && (context.getServer().getServerType() == ServerType.LoginServer);
    }

    @Override
    public void processMessage(ReadyMessage message, PluginContext context) throws ClientNotFoundException {
        switch (context.getServer().getServerType()) {
            case LoginServer:
                processMessageWithLoginServer(message, context);
                break;
        }
    }

    private void processMessageWithLoginServer(ReadyMessage readyMessage, PluginContext context) {
        LoginServer currentServer = (LoginServer) context.getServer();
        try {
            ServerBasic readyMessageServer = readyMessage.getMessageServer();
            currentServer.clientCacheProvider().registerClient(readyMessageServer, context.getCurrentHost(), context.getCurrentPort());
            currentServer.allocateProvider().messageServerReady(readyMessageServer);
        } catch (ServerInnerException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
