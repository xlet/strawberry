package cn.w.im.core.server;

import cn.w.im.core.MessageHandlerContext;
import cn.w.im.core.ServerType;
import cn.w.im.core.providers.status.DefaultMemberProviderImpl;
import cn.w.im.core.providers.status.MemberProvider;
import cn.w.im.core.message.Message;
import cn.w.im.core.message.server.ReadyMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Creator: JackieHan.
 * DateTime: 13-11-15 下午1:59.
 * Summary:消息服务器信息.
 */
public class MessageServer extends ScalableServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServer.class);

    private MemberProvider memberProvider;

    /**
     * 构造函数.
     *
     * @param port port.
     */
    public MessageServer(int port) {
        super(ServerType.MessageServer, port);
    }

    @Override
    public void start() {
        super.start();
        this.memberProvider = new DefaultMemberProviderImpl(this.messageProvider(), this.clientProvider());
    }

    @Override
    public void handlerMessage(MessageHandlerContext context) {
        super.handlerMessage(context);
        Message message = context.getMessage();
        switch (message.getMessageType()) {
            case Connected:
            case ConnectedResponse:
            case Connect:
            case Normal:
            case Token:
            case Logout:
                this.memberProvider.handlerMessage(context);
                break;
            default:
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("message[{}] is ignored!", message.getMessageType());
                }
                break;
        }
    }

    @Override
    protected void registeredAfter(MessageHandlerContext context) {
        ReadyMessage readyMessage = new ReadyMessage(this.getServerBasic());
        this.messageProvider().send(ServerType.LoginServer, readyMessage);
    }
}
