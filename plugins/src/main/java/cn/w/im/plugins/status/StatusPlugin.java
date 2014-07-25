package cn.w.im.plugins.status;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.PluginContext;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.basic.OnlineMemberStatus;
import cn.w.im.domains.basic.Status;
import cn.w.im.domains.messages.Message;
import cn.w.im.domains.messages.client.ConnectMessage;
import cn.w.im.domains.messages.client.LogoutMessage;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.persistent.OnLineMemberStatusDao;
import cn.w.im.plugins.MultiMessagePlugin;
import cn.w.im.server.MessageServer;
import cn.w.im.utils.spring.SpringContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * member status plugin.
 * <p/>
 * after {@link cn.w.im.plugins.connected.MessageClientConnectPlugin}.
 */
public class StatusPlugin extends MultiMessagePlugin {

    private final static Log LOG = LogFactory.getLog(StatusPlugin.class);

    private OnLineMemberStatusDao onLineMemberStatusDao;

    /**
     * 构造函数.
     *
     * @param containerType 服务类型.
     */
    public StatusPlugin(ServerType containerType) {
        super("memberStatusPlugin", "add online member to cache.", containerType);
        this.onLineMemberStatusDao = SpringContext.context().getBean(OnLineMemberStatusDao.class);
    }

    @Override
    protected boolean isMatch(PluginContext context) {
        MessageType messageType = context.getMessage().getMessageType();
        return ((messageType.equals(MessageType.Connect)) || (messageType.equals(MessageType.Logout)));
    }

    @Override
    protected void processMessage(Message message, PluginContext context) throws ClientNotFoundException, NotSupportedServerTypeException {

        switch (this.containerType()) {
            case MessageServer:
                processMessageWithMessageServer(message, context);
                break;
            default:
                throw new NotSupportedServerTypeException(this.containerType());
        }
    }

    private void processMessageWithMessageServer(Message message, PluginContext context) {
        if (message.getMessageType().equals(MessageType.Connect)) {
            String loginId = ((ConnectMessage) message).getLoginId();
            if (MessageServer.current().clientCacheProvider().getClients(loginId).size() != 0) {
                OnlineMemberStatus memberStatus = new OnlineMemberStatus(loginId, Status.Online);
                this.onLineMemberStatusDao.save(memberStatus);

            }
        } else if (message.getMessageType().equals(MessageType.Logout)) {
            String loginId = ((LogoutMessage) message).getLoginId();
            this.onLineMemberStatusDao.delete(loginId);
        }
    }
}
