package cn.w.im.plugins.logout;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.PluginContext;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.messages.client.LogoutMessage;
import cn.w.im.domains.messages.client.LogoutResponseMessage;
import cn.w.im.exceptions.ClientNotRegisterException;
import cn.w.im.exceptions.ServerNotRegisterException;
import cn.w.im.plugins.MessagePlugin;
import cn.w.im.server.MessageServer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-30 下午7:18.
 * Summary: 登出消息处理插件.
 */
public class LogoutPlugin extends MessagePlugin<LogoutMessage> {

    /**
     * 日志.
     */
    private Log logger = LogFactory.getLog(this.getClass());

    /**
     * 构造函数.
     *
     * @param containerType 服务类型.
     */
    public LogoutPlugin(ServerType containerType) {
        super("LogoutPlugin", "logout process.", containerType);
    }

    @Override
    public boolean isMatch(PluginContext context) {
        MessageType messageType = context.getMessage().getMessageType();
        return messageType.equals(MessageType.Logout);
    }

    @Override
    public void processMessage(LogoutMessage message, PluginContext context) {
        try {
            MessageServer.current().clientCacheProvider().removeClient(context.getCurrentHost(), context.getCurrentPort());
            LogoutResponseMessage logoutResponseMessage = new LogoutResponseMessage(true);
            //notify other message servers
            MessageServer.current().sendMessageProvider().send(ServerType.MessageServer, logoutResponseMessage);
            //response to client
            MessageServer.current().sendMessageProvider().send(message.getLoginId(), logoutResponseMessage);

        } catch (ServerNotRegisterException e) {
            logger.error(e.getMessage());
        } catch (ClientNotRegisterException e) {
            logger.error(e.getMessage());
        }

    }
}
