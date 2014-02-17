package cn.w.im.plugins.logout;

import cn.w.im.domains.HandlerContext;
import cn.w.im.domains.server.MessageServer;
import cn.w.im.domains.messages.LogoutMessage;
import cn.w.im.domains.messages.responses.LogoutResponseMessage;
import cn.w.im.domains.messages.Message;
import cn.w.im.domains.server.ServerType;
import cn.w.im.plugins.MessagePlugin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-30 下午7:18.
 * Summary: 登出消息处理插件.
 */
public class LogoutPlugin extends MessagePlugin {

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
        super("LogoutPlugin", "退出登陆处理!", containerType);
    }

    @Override
    public boolean isMatch(HandlerContext context) {
        return context.getMessage() instanceof LogoutMessage;
    }

    @Override
    public void processMessage(Message message, HandlerContext context) {
        try {
            LogoutMessage logoutMessage = (LogoutMessage) message;
            if (logout(logoutMessage)) {
                context.write(new LogoutResponseMessage(true));
            } else {
                context.write(new LogoutResponseMessage(false));
            }
        } catch (Exception ex) {
            logger.error("logout Error!", ex);
            context.write(new LogoutResponseMessage(false));
        }
    }

    private boolean logout(LogoutMessage message) throws Exception {
        MessageServer.current().removeClient(message.getLoginId());
        return true;
    }
}