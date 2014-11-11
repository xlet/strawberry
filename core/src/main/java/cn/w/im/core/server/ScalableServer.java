package cn.w.im.core.server;

import cn.w.im.core.MessageHandlerContext;
import cn.w.im.core.ServerType;
import cn.w.im.core.message.Message;
import cn.w.im.core.message.server.ServerRegisterMessage;
import cn.w.im.core.message.server.ServerRegisterResponseMessage;
import cn.w.im.core.exception.ServerInnerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * scalable server.
 */
public abstract class ScalableServer extends AbstractServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScalableServer.class);

    /**
     * 构造函数.
     *
     * @param serverType 服务类型.
     * @param port
     */
    public ScalableServer(ServerType serverType, int port) {
        super(serverType, port);
    }

    @Override
    protected void handlerMessage(MessageHandlerContext context) {
        Message message = context.getMessage();
        switch (message.getMessageType()) {
            case ForwardReady: //forward server ready then register self to bus server.
                this.registerToBus(context.getCurrentHost(), context.getCurrentPort());
                break;
            case ServerRegisterResponse:
                this.registered(context);
                break;
        }
    }

    private void registered(MessageHandlerContext context) {
        try {
            ServerRegisterResponseMessage responseMessage = (ServerRegisterResponseMessage) context.getMessage();
            if (responseMessage.isSuccess()) {
                Collection<ServerBasic> startedServers = responseMessage.getStartedServers();
                for (ServerBasic startedServer : startedServers) {
                    this.clientProvider().registerClient(context.getChannel(), startedServer);
                }
                this.registeredAfter(context);
            } else {
                LOGGER.error("core[" + responseMessage.getFromServer().getNodeId() + "] perhaps error! errorCode[" + responseMessage.getErrorCode() + "] errorMessage:" + responseMessage.getErrorMessage());
            }
        } catch (ServerInnerException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    protected abstract void registeredAfter(MessageHandlerContext context);


    private void registerToBus(String host, int port) {
        ServerRegisterMessage serverRegisterMessage = new ServerRegisterMessage(this.getServerBasic());
        this.messageProvider().send(host, port, serverRegisterMessage);
    }
}
