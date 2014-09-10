package cn.w.im.plugins.nearlyLinkman;

import cn.w.im.domains.MessageType;
import cn.w.im.core.plugins.PluginContext;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.basic.NearlyLinkman;
import cn.w.im.domains.messages.client.NormalMessage;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.persistent.NearlyLinkmanDao;
import cn.w.im.core.plugins.MessagePlugin;
import cn.w.im.utils.spring.SpringContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * nearly linkman plugin.
 */
public class NearlyLinkmanPlugin extends MessagePlugin<NormalMessage> {

    private final static Log LOG = LogFactory.getLog(NearlyLinkmanPlugin.class);
    private NearlyLinkmanDao nearlyLinkmanDao;

    private final ObjectMapper mapper = new ObjectMapper();


    /**
     * 构造函数.
     */
    public NearlyLinkmanPlugin() {
        super("nearly linkman plugin", "maintain nearly linkman relations.");
        this.nearlyLinkmanDao = SpringContext.context().getBean(NearlyLinkmanDao.class);
    }

    @Override
    public boolean isMatch(PluginContext context) {
        return (context.getMessage().getMessageType() == MessageType.Normal)
                && (context.getServer().getServerType() == ServerType.MessageServer);
    }

    @Override
    protected void processMessage(NormalMessage message, PluginContext context) throws ClientNotFoundException {
        NearlyLinkman nearlyLinkman = nearlyLinkmanDao.get(message.getFrom(), message.getTo());
        if (nearlyLinkman == null) {
            LOG.debug("create new link relations.");
            nearlyLinkman = new NearlyLinkman(message.getFrom(), message.getTo());
            try {
                LOG.debug(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(nearlyLinkman));
            } catch (JsonProcessingException e) {
                LOG.error(e);
            }
            this.nearlyLinkmanDao.save(nearlyLinkman);
        } else {
            LOG.debug("update existed link relations.");
            int count = this.nearlyLinkmanDao.updateLinkTime(nearlyLinkman);
            LOG.debug("update " + count + " relations.");
        }
    }
}
