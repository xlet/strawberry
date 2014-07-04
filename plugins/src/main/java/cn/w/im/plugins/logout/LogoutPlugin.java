package cn.w.im.plugins.logout;

import cn.w.im.domains.PluginContext;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.messages.client.LogoutMessage;
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
        return context.getMessage() instanceof LogoutMessage;
    }

    @Override
    public void processMessage(LogoutMessage message, PluginContext context) {
        try {
            MessageServer.current().clientCacheProvider().removeClient(context.getCurrentHost(), context.getCurrentPort());
        } catch (ServerNotRegisterException e) {
            e.printStackTrace();
        } catch (ClientNotRegisterException e) {
            e.printStackTrace();
        }
    }
}
