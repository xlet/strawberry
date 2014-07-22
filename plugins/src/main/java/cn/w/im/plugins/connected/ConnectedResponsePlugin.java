package cn.w.im.plugins.connected;

import cn.w.im.domains.ConnectToken;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.PluginContext;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.basic.Member;
import cn.w.im.domains.messages.client.ConnectResponseMessage;
import cn.w.im.domains.messages.client.NormalMessage;
import cn.w.im.domains.messages.server.ConnectedResponseMessage;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.exceptions.ServerInnerException;
import cn.w.im.plugins.MessagePlugin;
import cn.w.im.server.MessageServer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-24 下午9:12.
 * Summary: the message server who send ConnectedMessage must waiting to received all server send ConnectedResponseMessage then send ConnectResponseMessage to message client.
 */
public class ConnectedResponsePlugin extends MessagePlugin<ConnectedResponseMessage> {

    private final Log logger;

    /**
     * 构造函数.
     *
     * @param containerType 服务类型.
     */
    public ConnectedResponsePlugin(ServerType containerType) {
        super("ConnectedResponsePlugin", "response connect message.", containerType);
        logger = LogFactory.getLog(this.getClass());
    }

    @Override
    protected boolean isMatch(PluginContext context) {
        return context.getMessage().getMessageType() == MessageType.ConnectedResponse;
    }

    @Override
    protected void processMessage(ConnectedResponseMessage message, PluginContext context) throws ClientNotFoundException, NotSupportedServerTypeException {
        switch (this.containerType()) {
            case MessageServer:
                processMessageWithMessageServer(message, context);
                break;
            default:
                throw new NotSupportedServerTypeException(this.containerType());
        }
    }

    private void processMessageWithMessageServer(ConnectedResponseMessage message, PluginContext context) {
        ConnectToken connectToken = MessageServer.current().getToken(message.getToken());
        try {
            if (message.isSuccess()) {
                MessageServer.current().respondProvider().receivedRespondedMessage(message);
                if (MessageServer.current().respondProvider().allResponded(message.getRespondKey())) {
                    MessageServer messageServer = MessageServer.current();
                    messageServer.connected(message.getToken());
                    //获取最近联系人列表
                    List<Member> recentChatWith = messageServer.linkmanProvider().getNearlyLinkmen(connectToken.getLoginId());
                    //联系人状态
                    messageServer.statusProvider().render(recentChatWith);
                    ConnectResponseMessage responseMessage = new ConnectResponseMessage();
                    responseMessage.setNearlyLinkmen(recentChatWith);
                    //当前用户信息
                    responseMessage.setSelf(messageServer.linkmanProvider().getMember(connectToken.getLoginId()));
                    //离线消息列表
                    List<NormalMessage> offlineMessages = messageServer.messageProvider().getOfflineMessages(connectToken.getLoginId());
                    responseMessage.setOfflineMessages(offlineMessages);
                    messageServer.sendMessageProvider().send(connectToken.getLoginId(), responseMessage);
                    //将离线消息状态标记为已送达
                    messageServer.messageProvider().setMessageForwarded(connectToken.getLoginId());
                }
            } else {
                logger.error("server[" + message.getFromServer().getNodeId() + "] perhaps error! errorCode[" + message.getErrorCode() + "] errorMessage:" + message.getErrorMessage());
                MessageServer.current().respondProvider().interrupt(message.getRespondKey());
            }
        } catch (ServerInnerException ex) {
            logger.error(ex.getMessage(), ex);
            ConnectResponseMessage errorResponseMessage = new ConnectResponseMessage(ex.getErrorCode(), ex.getMessage());
            MessageServer.current().sendMessageProvider().send(connectToken.getLoginId(), errorResponseMessage);
        }
    }
}
