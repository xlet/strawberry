package cn.w.im.core.server;

import cn.w.im.core.MessageHandlerContext;
import cn.w.im.core.providers.client.ClientProvider;
import cn.w.im.core.providers.client.DefaultClientProvider;
import cn.w.im.core.providers.message.DefaultRespondProvider;
import cn.w.im.core.providers.message.RespondProvider;
import cn.w.im.core.providers.message.DefaultMessageProviderImpl;
import cn.w.im.core.providers.message.MessageProvider;
import cn.w.im.core.providers.persistent.MessagePersistentProvider;
import cn.w.im.core.providers.persistent.PersistentProviderFactory;
import cn.w.im.core.message.NonePersistentMessage;
import cn.w.im.core.ServerType;
import cn.w.im.core.message.Message;
import cn.w.im.core.message.forward.ForwardResponseMessage;
import cn.w.im.core.exception.ServerInnerException;
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
     * 构造函数.
     *
     * @param serverType 服务类型.
     */
    public AbstractServer(ServerType serverType, int port) {
        this.serverBasic = new ServerBasic(serverType, this.getLocalHost(), port);
    }

    private String getLocalHost() {
        try {
            InetAddress localAddress = InetAddress.getLocalHost();
            return localAddress.getHostAddress().toString();
        } catch (UnknownHostException e) {
            LOGGER.warn("get local host error!127.0.0.1 instead!", e);
            return "127.0.0.1";
        }
    }

    /**
     * 服务启动时调用.
     */
    public void start() {
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
    public final void messageArrived(MessageHandlerContext context) {
        Message message = context.getMessage();
        switch (message.getMessageType()) {
            case ForwardRequest:  //forward service request server basic info  message.
                this.sendBasic(context.getCurrentHost(), context.getCurrentPort());
                break;
            default:
                handlerMessage(context);
                break;
        }
        if (!(message instanceof NonePersistentMessage)) {
            persistentMessage(message);
        }
    }

    protected abstract void handlerMessage(MessageHandlerContext context);

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
        return this.serverBasic.getHost();
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
        this.serverBasic.setHost(host);
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
