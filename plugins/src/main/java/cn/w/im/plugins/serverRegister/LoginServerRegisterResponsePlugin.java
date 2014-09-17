package cn.w.im.plugins.serverRegister;

import cn.w.im.core.plugins.PluginContext;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.messages.server.ServerRegisterResponseMessage;
import cn.w.im.exceptions.ServerInnerException;
import cn.w.im.domains.ServerType;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.core.plugins.MessagePlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-18 下午8:35.
 * Summary: process login server register response message.
 * add response started other login server.
 * <p/>
 * this plugin only added to LoginServer.
 */
public class LoginServerRegisterResponsePlugin extends MessagePlugin<ServerRegisterResponseMessage> {

    private Logger logger;

    /**
     * 构造函数.
     */
    public LoginServerRegisterResponsePlugin() {
        super("LoginServerRegisterResponsePlugin", "add response started core basic.");
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public boolean isMatch(PluginContext context) {
        return (context.getMessage().getMessageType() == MessageType.ServerRegisterResponse)
                && (context.getServer().getServerType() == ServerType.LoginServer);
    }

    @Override
    public void processMessage(ServerRegisterResponseMessage message, PluginContext context) throws ClientNotFoundException {
        switch (context.getServer().getServerType()) {
            case LoginServer:
                processMessageWithLoginServer(message, context);
                break;
        }
    }

    private void processMessageWithLoginServer(ServerRegisterResponseMessage responseMessage, PluginContext context) {
        try {
            if (responseMessage.isSuccess()) {
                List<ServerBasic> startedServers = responseMessage.getStartedServers();
                for (ServerBasic startedServer : startedServers) {
                    context.getServer().clientCacheProvider().registerClient(startedServer, context.getCurrentHost(), context.getCurrentPort());
                }
                //todo:jackie register to message server and request message server linked clients count.
            } else {
                logger.error("core[" + responseMessage.getFromServer().getNodeId() + "] perhaps error! errorCode[" + responseMessage.getErrorCode() + "] errorMessage:" + responseMessage.getErrorMessage());
            }
        } catch (ServerInnerException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
