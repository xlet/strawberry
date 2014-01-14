package cn.w.im.plugins.login;

import cn.w.im.domains.client.MessageClient;
import cn.w.im.domains.HandlerContext;
import cn.w.im.domains.LoginToken;
import cn.w.im.domains.server.MessageServer;
import cn.w.im.domains.messages.LoginMessage;
import cn.w.im.domains.messages.responses.LoginResponseMessage;
import cn.w.im.domains.messages.Message;
import cn.w.im.domains.mongo.MongoLoginToken;
import cn.w.im.mongo.dao.utils.MongoLoginTokenDao;
import cn.w.im.plugins.MessagePlugin;
import cn.w.im.utils.netty.IpAddressProvider;
import cn.w.im.utils.spring.SpringContext;
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

    private MongoLoginTokenDao mongoLoginTokenDao;

    /**
     * 构造函数.
     */
    public LoginPlugin() {
        super("LoginPlugin", "登陆处理!");
        mongoLoginTokenDao = (MongoLoginTokenDao) SpringContext.context().getBean("mongoLoginTokenDao");
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
                MessageClient client = new MessageClient(context.getCtx(), loginMessage.getLoginId());
                MessageServer.current().addClient(client);

                LoginToken token = createAndSaveToken(client);
                context.write(new LoginResponseMessage(true, token));
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

    private LoginToken createAndSaveToken(MessageClient client) {
        LoginToken token = new LoginToken();
        token.setLoginId(client.getId());
        token.setClientIp(IpAddressProvider.getRemoteIpAddress(client.getContext()));
                token.setLoginDate(new Date());
        token.setUsed(false);
        token.setToken(UUID.randomUUID().toString().replace("-", ""));

        MongoLoginToken mongoLoginToken = new MongoLoginToken(token);
        mongoLoginTokenDao.save(mongoLoginToken);

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
