package org.xlet.strawberry.core.server;

import org.xlet.strawberry.core.MessageHandlerContext;
import org.xlet.strawberry.core.message.Message;
import org.xlet.strawberry.core.message.server.ServerRegisterMessage;
import org.xlet.strawberry.core.message.server.ServerRegisterResponseMessage;
import org.xlet.strawberry.core.exception.ServerInnerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;
import java.util.Collection;

/**
 * scalable server.
 */
public abstract class ScalableServer extends AbstractServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScalableServer.class);

    /**
     * 构造函数.
     *
     * @param serverType server type.
     * @param outerHost  outer host.
     * @param port       listening port.
     * @throws UnknownHostException can not get lan ip,throw this exception.
     */
    public ScalableServer(ServerType serverType, String outerHost, int port) throws UnknownHostException {
        super(serverType, outerHost, port);
    }

    @Override
    protected void handlerMessage(MessageHandlerContext context) throws ServerInnerException {
        Message message = context.getMessage();
        switch (message.getMessageType()) {
            case ForwardReady: //forward server ready then register self to bus server.
                this.registerToBus(context.getHost(), context.getPort());
                break;
            case ServerRegisterResponse:
                this.registered(context);
                break;
            default:
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("message[{}] is ignore!", message.getMessageType());
                }
                break;
        }
    }

    private void registered(MessageHandlerContext context) {
        try {
            String host = context.getHost();
            int port = context.getPort();
            ServerRegisterResponseMessage
                    responseMessage = (ServerRegisterResponseMessage) context.getMessage();
            if (responseMessage.isSuccess()) {
                ServerBasic serverBasic = responseMessage.getFromServer();
                this.clientProvider().registerClient(host, port, serverBasic);
                Collection<ServerBasic> startedServers = responseMessage.getStartedServers();
                for (ServerBasic startedServer : startedServers) {
                    this.clientProvider().registerClient(host, port, startedServer);
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
