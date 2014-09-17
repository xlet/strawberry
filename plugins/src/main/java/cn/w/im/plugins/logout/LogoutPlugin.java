package cn.w.im.plugins.logout;

import cn.w.im.domains.MessageType;
import cn.w.im.core.plugins.PluginContext;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.client.Client;
import cn.w.im.domains.messages.client.LogoutMessage;
import cn.w.im.domains.messages.client.LogoutResponseMessage;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.ClientNotRegisterException;
import cn.w.im.exceptions.ServerNotRegisterException;
import cn.w.im.core.plugins.MessagePlugin;
import cn.w.im.core.server.MessageServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-30 下午7:18.
 * Summary: 登出消息处理插件.
 */
public class LogoutPlugin extends MessagePlugin<LogoutMessage> {

    /**
     * 日志.
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 构造函数.
     */
    public LogoutPlugin() {
        super("LogoutPlugin", "logout process.");
    }

    @Override
    public boolean isMatch(PluginContext context) {
        return (context.getMessage().getMessageType().equals(MessageType.Logout))
                && (context.getServer().getServerType() == ServerType.MessageServer);
    }

    @Override
    public void processMessage(LogoutMessage message, PluginContext context) {
        try {
            MessageServer currentServer = (MessageServer) context.getServer();
            Client client = currentServer.clientCacheProvider().getClient(message.getClientType(), message.getLoginId());

            LogoutResponseMessage logoutResponseMessage = new LogoutResponseMessage(true);
            //notify other message servers
            currentServer.messageProvider().send(ServerType.MessageServer, message);
            //response to client
            //TODO if client has been removed, send call will not work
            currentServer.messageProvider().send(message.getLoginId(), logoutResponseMessage);
            //unregister the client
            currentServer.clientCacheProvider().removeClient(context.getCurrentHost(), context.getCurrentPort());
            //close the client
            if (client != null) {
                client.close();
            }
        } catch (ServerNotRegisterException e) {
            logger.error(e.getMessage());
        } catch (ClientNotRegisterException e) {
            logger.error(e.getMessage());
        } catch (ClientNotFoundException e) {
            logger.error(e.getMessage());
        }

    }
}
