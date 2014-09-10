package cn.w.im.plugins.status;

import cn.w.im.domains.MessageType;
import cn.w.im.core.plugins.PluginContext;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.basic.OnlineMemberStatus;
import cn.w.im.domains.basic.Status;
import cn.w.im.domains.messages.Message;
import cn.w.im.domains.messages.client.ConnectMessage;
import cn.w.im.domains.messages.client.LogoutMessage;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.persistent.OnlineMemberStatusDao;
import cn.w.im.core.plugins.MultiMessagePlugin;
import cn.w.im.core.server.MessageServer;
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

    private OnlineMemberStatusDao onlineMemberStatusDao;

    /**
     * 构造函数.
     */
    public StatusPlugin() {
        super("memberStatusPlugin", "add online member to cache.");
        this.onlineMemberStatusDao = SpringContext.context().getBean(OnlineMemberStatusDao.class);
    }

    @Override
    public boolean isMatch(PluginContext context) {
        MessageType messageType = context.getMessage().getMessageType();
        return (((messageType.equals(MessageType.Connect)) || (messageType.equals(MessageType.Logout)))
                && (context.getServer().getServerType() == ServerType.MessageServer));
    }

    @Override
    protected void processMessage(Message message, PluginContext context) throws ClientNotFoundException {

        switch (context.getServer().getServerType()) {
            case MessageServer:
                processMessageWithMessageServer(message, context);
                break;
        }
    }

    private void processMessageWithMessageServer(Message message, PluginContext context) {
        if (message.getMessageType().equals(MessageType.Connect)) {
            String loginId = ((ConnectMessage) message).getLoginId();
            if (context.getServer().clientCacheProvider().getClients(loginId).size() != 0) {
                OnlineMemberStatus memberStatus = new OnlineMemberStatus(loginId, Status.Online);
                this.onlineMemberStatusDao.save(memberStatus);

            }
        } else if (message.getMessageType().equals(MessageType.Logout)) {
            String loginId = ((LogoutMessage) message).getLoginId();
            this.onlineMemberStatusDao.delete(loginId);
        }
    }
}
