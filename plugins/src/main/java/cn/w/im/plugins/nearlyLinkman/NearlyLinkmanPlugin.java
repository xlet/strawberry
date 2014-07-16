package cn.w.im.plugins.nearlyLinkman;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.PluginContext;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.basic.NearlyLinkman;
import cn.w.im.domains.messages.client.NormalMessage;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.persistent.NearlyLinkmanDao;
import cn.w.im.plugins.MessagePlugin;
import cn.w.im.utils.spring.SpringContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * nearly linkman plugin.
 */
public class NearlyLinkmanPlugin extends MessagePlugin<NormalMessage> {

    private final static Log LOG = LogFactory.getLog(NearlyLinkmanPlugin.class);
    private NearlyLinkmanDao nearlyLinkmanDao;

    /**
     * 构造函数.
     *
     * @param containerType 服务类型.
     */
    public NearlyLinkmanPlugin(ServerType containerType) {
        super("nearly linkman plugin", "maintain nearly linkman relations.", containerType);
        this.nearlyLinkmanDao = SpringContext.context().getBean(NearlyLinkmanDao.class);
    }

    @Override
    protected boolean isMatch(PluginContext context) {
        MessageType messageType = context.getMessage().getMessageType();
        return messageType == MessageType.Normal;
    }

    @Override
    protected void processMessage(NormalMessage message, PluginContext context) throws ClientNotFoundException, NotSupportedServerTypeException {
        NearlyLinkman nearlyLinkman = nearlyLinkmanDao.get(message.getFrom(), message.getTo());
        if (nearlyLinkman == null) {
            LOG.debug("create new link relations.");
            nearlyLinkman = new NearlyLinkman(message.getFrom(), message.getTo());
            this.nearlyLinkmanDao.save(nearlyLinkman);
        } else {
            LOG.debug("update existed link relations.");
            int count = this.nearlyLinkmanDao.updateLinkTime(nearlyLinkman);
            LOG.debug("update " + count + " relations.");
        }
    }
}
