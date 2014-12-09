package org.xlet.strawberry.core.server;

import org.xlet.strawberry.core.MessageHandlerContext;
import org.xlet.strawberry.core.client.provider.ClientProvider;
import org.xlet.strawberry.core.client.provider.DefaultClientProvider;
import org.xlet.strawberry.core.message.provider.DefaultRespondProvider;
import org.xlet.strawberry.core.message.provider.RespondProvider;
import org.xlet.strawberry.core.message.provider.DefaultMessageProviderImpl;
import org.xlet.strawberry.core.message.provider.MessageProvider;
import org.xlet.strawberry.core.message.persistent.MessagePersistentProvider;
import org.xlet.strawberry.core.persistent.PersistentProviderFactory;
import org.xlet.strawberry.core.message.NonePersistentMessage;
import org.xlet.strawberry.core.message.Message;
import org.xlet.strawberry.core.message.forward.ForwardResponseMessage;
import org.xlet.strawberry.core.exception.ServerInnerException;
import org.xlet.strawberry.core.util.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 上午9:56.
 * Summary: 抽象服务信息类.
 */
public abstract class AbstractServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractServer.class);

    private ServerBasic serverBasic;

    private ClientProvider clientProvider;
    private MessageProvider messageProvider;
    private RespondProvider respondProvider;


    /**
     * constructor.
     *
     * @param serverType server type.
     * @param outerHost  outer host.
     * @throws UnknownHostException can not get lan ip,throw this exception.
     */
    public AbstractServer(ServerType serverType, String outerHost, int port) throws UnknownHostException {
        InetAddress localAddress = IpUtils.getLocalHostLANAddress();
        this.serverBasic = new ServerBasic(serverType, localAddress.getHostAddress(), outerHost, port);
    }

    /**
     * 服务启动时调用.
     */
    public void start() throws ServerInnerException {
        if (!this.serverBasic.isStart()) {
            this.serverBasic.setStart(true);
            this.serverBasic.setStartDateTime(new Date().getTime());
            this.clientProvider = new DefaultClientProvider();
            this.respondProvider = new DefaultRespondProvider();
            this.messageProvider = new DefaultMessageProviderImpl(this.clientProvider, this.respondProvider, this.serverBasic);
        }
    }

    /**
     * 服务关闭时调用.
     */
    public void stop() {
        if (this.serverBasic.isStart()) {
            this.serverBasic.setStart(false);
        }
    }

    /**
     * 服务是否启动.
     *
     * @return true:启动.
     */
    public boolean isStart() {
        return this.serverBasic.isStart();
    }

    /**
     * message arrived.
     *
     * @param context message handler context.
     */
    public final void messageArrived(MessageHandlerContext context) throws ServerInnerException {
        Message message = context.getMessage();
        switch (message.getMessageType()) {
            case ForwardRequest:  //forward service request server basic info  message.
                this.sendBasic(context.getHost(), context.getPort());
                break;
            default:
                handlerMessage(context);
                break;
        }
        if (!(message instanceof NonePersistentMessage)) {
            persistentMessage(message);
        }
    }

    protected abstract void handlerMessage(MessageHandlerContext context) throws ServerInnerException;

    /**
     * send request client this server basic info.
     *
     * @param host client host.
     * @param port client port.
     */
    private void sendBasic(String host, int port) {
        ForwardResponseMessage responseMessage = new ForwardResponseMessage(this.getServerBasic());
        this.messageProvider().send(host, port, responseMessage);
    }

    private void persistentMessage(Message message) {
        try {
            MessagePersistentProvider messagePersistentProvider = PersistentProviderFactory.getMessagePersistentProvider(message);
            messagePersistentProvider.save(message);
        } catch (ServerInnerException ex) {
            LOGGER.error("persistent message error", ex);
        }
    }

    /**
     * 启动时间.
     *
     * @return true:启动.
     */
    public long getStartDate() {
        return this.serverBasic.getStartDateTime();
    }

    /**
     * 获取绑定host.
     *
     * @return host.
     */
    public String getHost() {
        return this.serverBasic.getLanHost();
    }

    /**
     * 获取监听端口号.
     *
     * @return 端口号.
     */
    public int getPort() {
        return this.serverBasic.getPort();
    }

    /**
     * 获取服务标识.
     *
     * @return 标识.
     */
    public String getNodeId() {
        return this.serverBasic.getNodeId();
    }

    /**
     * 获取服务基本信息.
     *
     * @return 获取服务基本信息.
     */
    public ServerBasic getServerBasic() {
        return this.serverBasic;
    }

    /**
     * 获取服务类型.
     *
     * @return 服务类型.
     */
    public ServerType getServerType() {
        return this.serverBasic.getServerType();
    }

    /**
     * 设置绑定host.
     *
     * @param host host.
     */
    protected void setHost(String host) {
        this.serverBasic.setLanHost(host);
    }

    /**
     * 设置监听端口.
     *
     * @param port 端口号.
     */
    protected void setPort(int port) {
        this.serverBasic.setPort(port);
    }

    /**
     * get client cache provider.
     *
     * @return client cache provider.
     */
    public ClientProvider clientProvider() {
        return clientProvider;
    }

    /**
     * get send message provider.
     *
     * @return send message provider.
     */
    public MessageProvider messageProvider() {
        return this.messageProvider;
    }

    /**
     * get respond provider.
     *
     * @return respond provider.
     */
    public RespondProvider respondProvider() {
        return respondProvider;
    }
}
