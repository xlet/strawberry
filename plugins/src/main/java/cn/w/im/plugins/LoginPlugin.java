package cn.w.im.plugins;

import cn.w.im.domains.ClientInfo;
import cn.w.im.domains.HandlerContext;
import cn.w.im.domains.ServerInfo;
import cn.w.im.domains.messages.LoginMessage;
import cn.w.im.domains.messages.LoginResponseMessage;
import cn.w.im.domains.messages.Message;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-30 下午5:17.
 * Summary: 登陆消息处理插件.
 */
public class LoginPlugin extends MessagePlugin {

    private Log logger = LogFactory.getLog(this.getClass());

    /**
     * 构造函数.
     */
    public LoginPlugin() {
        super("LoginPlugin", "登陆处理!");
    }

    @Override
    public boolean isMatch(HandlerContext context) {
        return context.getMessage() instanceof LoginMessage;
    }

    @Override
    public void processMessage(Message message, HandlerContext context) {
        try {
            logger.info("开始登陆处理!");
            LoginMessage loginMessage = (LoginMessage) message;
            if (login(loginMessage)) {
                ClientInfo client = new ClientInfo(context, loginMessage.getLoginId());
                ServerInfo.current().addClient(client);
                context.write(new LoginResponseMessage(true));
                logger.info("登陆成功!");
            } else {
                context.write(new LoginResponseMessage(false));
                logger.info("登陆失败!");
            }
        } catch (Exception ex) {
            logger.error("登陆失败!", ex);
            context.write(new LoginResponseMessage(false));
        }
    }

    private boolean login(LoginMessage message) {
        String loginId = message.getLoginId();
        String password = message.getPassword();
        //TODO:jackie 验证用户名密码
        //TODO:jackie 验证是否重复登陆，如有则发送强制退出命令给前一个客户端
        return true;
    }
}
