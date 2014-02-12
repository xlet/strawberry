package cn.w.im.plugins.login;

import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.client.MessageClient;
import cn.w.im.domains.HandlerContext;
import cn.w.im.domains.LoginToken;
import cn.w.im.domains.messages.ForwardMessage;
import cn.w.im.domains.messages.LoginMessage;
import cn.w.im.domains.messages.TokenMessage;
import cn.w.im.domains.messages.responses.LoginResponseMessage;
import cn.w.im.domains.messages.Message;
import cn.w.im.domains.server.LoginServer;
import cn.w.im.domains.server.ServerType;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.plugins.MessagePlugin;
import cn.w.im.utils.netty.IpAddressProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.UUID;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-30 下午5:17.
 * Summary: 登陆消息处理插件.
 */
public class LoginPlugin extends MessagePlugin {

    private Log logger = LogFactory.getLog(this.getClass());

    /**
     * 构造函数.
     *
     * @param containerType 服务类型.
     */
    public LoginPlugin(ServerType containerType) {
        super("LoginPlugin", "登陆处理!", containerType);
    }

    @Override
    public boolean isMatch(HandlerContext context) {
        return context.getMessage() instanceof LoginMessage;
    }

    @Override
    public void processMessage(Message message, HandlerContext context) {
        LoginMessage loginMessage = (LoginMessage) message;
        switch (this.containerType()) {
            case LoginServer:
                processWithLoginServer(loginMessage, context);
                break;
            default:
                throw new NotSupportedServerTypeException(this.containerType());
        }
    }

    private void processWithLoginServer(LoginMessage message, HandlerContext context) {
        if (login(message)) {
            MessageClient client = new MessageClient(context.getCtx(), message.getLoginId());
            LoginToken token = createToken(client);

            //通知消息服务登陆token信息.
            ServerBasic messageServer = LoginServer.current().getMessageServer();
            TokenMessage tokenMessage = new TokenMessage(token);
            ForwardMessage forwardMessage = new ForwardMessage(LoginServer.current().getServerBasic(), messageServer, tokenMessage);
            LoginServer.current().getForwardContext().writeAndFlush(forwardMessage);

            //发送登陆token给客户端. 并关闭连接.
            LoginResponseMessage loginResponseMessage = new LoginResponseMessage(true, token, messageServer);
            context.write(loginResponseMessage);
        } else {
            context.write(new LoginResponseMessage(false));
        }
        context.close();
    }

    private LoginToken createToken(MessageClient client) {
        LoginToken token = new LoginToken();
        token.setLoginId(client.getId());
        token.setClientIp(IpAddressProvider.getRemoteIpAddress(client.getContext()));
        token.setLoginDate(new Date());
        token.setToken(UUID.randomUUID().toString().replace("-", ""));

        return token;
    }

    private boolean login(LoginMessage message) {
        String loginId = message.getLoginId();
        String password = message.getPassword();
        //TODO:jackie 验证用户名密码
        //TODO:jackie 验证是否重复登陆，如有则发送强制退出命令给前一个客户端
        return true;
    }
}
