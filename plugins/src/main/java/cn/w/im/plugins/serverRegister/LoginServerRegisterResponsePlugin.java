package cn.w.im.plugins.serverRegister;

import cn.w.im.domains.PluginContext;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.messages.server.ServerRegisterResponseMessage;
import cn.w.im.exceptions.ServerInnerException;
import cn.w.im.core.server.LoginServer;
import cn.w.im.domains.ServerType;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.plugins.MessagePlugin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

    private Log logger;

    /**
     * 构造函数.
     */
    public LoginServerRegisterResponsePlugin(ServerType containerType) {
        super("LoginServerRegisterResponsePlugin", "add response started core basic.", containerType);
        logger = LogFactory.getLog(this.getClass());
    }

    @Override
    public boolean isMatch(PluginContext context) {
        boolean isMatch = context.getMessage().getMessageType() == MessageType.ServerRegisterResponse;
        return isMatch;
    }

    @Override
    public void processMessage(ServerRegisterResponseMessage message, PluginContext context) throws ClientNotFoundException, NotSupportedServerTypeException {
        switch (containerType()) {
            case LoginServer:
                processMessageWithLoginServer(message, context);
                break;
            default:
                throw new NotSupportedServerTypeException(containerType());
        }
    }

    private void processMessageWithLoginServer(ServerRegisterResponseMessage responseMessage, PluginContext context) {
        try {
            if (responseMessage.isSuccess()) {
                List<ServerBasic> startedServers = responseMessage.getStartedServers();
                for (ServerBasic startedServer : startedServers) {
                    LoginServer.current().clientCacheProvider().registerClient(startedServer, context.getCurrentHost(), context.getCurrentPort());
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
