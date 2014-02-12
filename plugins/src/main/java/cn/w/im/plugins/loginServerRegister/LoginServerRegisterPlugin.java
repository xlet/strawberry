package cn.w.im.plugins.loginServerRegister;

import cn.w.im.domains.HandlerContext;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.messages.Message;
import cn.w.im.domains.messages.ServerRegisterMessage;
import cn.w.im.domains.server.MessageBus;
import cn.w.im.domains.server.ServerType;
import cn.w.im.plugins.MessagePlugin;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 下午7:22.
 * Summary:
 */
public class LoginServerRegisterPlugin extends MessagePlugin {
    /**
     * 构造函数.
     *
     * @param containerType 服务类型.
     */
    public LoginServerRegisterPlugin(ServerType containerType) {
        super("loginServerRegisterPlugin", "登陆服务器注册插件", containerType);
    }

    @Override
    public boolean isMatch(HandlerContext context) {
        boolean isMatch = context.getMessage().getMessageType() == MessageType.ServerRegister;
        if (!isMatch) {
            return false;
        }
        ServerRegisterMessage serverRegisterMessage = (ServerRegisterMessage) context.getMessage();
        return serverRegisterMessage.getServerType() == ServerType.LoginServer;
    }

    @Override
    public void processMessage(Message message, HandlerContext context) {
        ServerRegisterMessage registerMessage = (ServerRegisterMessage) message;
        ServerBasic loginServer = registerMessage.getServerBasic();
        MessageBus.current().addLoginServer(loginServer, context.getCtx());

        //TODO: jackie 添加回复消息.
    }
}
