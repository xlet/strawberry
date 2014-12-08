package cn.w.im.core.server;

import cn.w.im.core.MessageHandlerContext;
import cn.w.im.core.exception.ServerInnerException;
import cn.w.im.core.status.memberAll.DefaultMemberAllProviderImpl;
import cn.w.im.core.status.memberAll.MemberAllProvider;
import cn.w.im.core.message.Message;
import cn.w.im.core.message.server.ReadyMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;


/**
 * Creator: JackieHan.
 * DateTime: 13-11-15 下午1:59.
 * Summary:消息服务器信息.
 */
public class MessageServer extends ScalableServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServer.class);

    private MemberAllProvider memberProvider;

    /**
     * 构造函数.
     *
     * @param outerHost outer host.
     * @param port      listening port.
     */
    public MessageServer(String outerHost, int port) throws UnknownHostException {
        super(ServerType.MessageServer, outerHost, port);
    }

    @Override
    public void start() throws ServerInnerException {
        super.start();
        this.memberProvider = new DefaultMemberAllProviderImpl(this.messageProvider(), this.clientProvider());
    }

    @Override
    public void handlerMessage(MessageHandlerContext context) throws ServerInnerException {
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
                super.handlerMessage(context);
                break;
        }
    }

    @Override
    protected void registeredAfter(MessageHandlerContext context) {
        ReadyMessage readyMessage = new ReadyMessage(this.getServerBasic());
        this.messageProvider().send(ServerType.LoginServer, readyMessage);
    }
}
