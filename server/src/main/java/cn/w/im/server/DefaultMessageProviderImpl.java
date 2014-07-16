package cn.w.im.server;

import cn.w.im.domains.conf.Configuration;
import cn.w.im.domains.messages.client.NormalMessage;
import cn.w.im.exceptions.ServerInnerException;
import cn.w.im.persistent.NormalMessageDao;
import cn.w.im.persistent.PersistentRepositoryFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * default implement {@link cn.w.im.server.MessageProvider}
 */
public class DefaultMessageProviderImpl implements MessageProvider {

    private final static Log LOG = LogFactory.getLog(DefaultMessageProviderImpl.class);

    private NormalMessageDao normalMessageDao;


    public DefaultMessageProviderImpl() {
        try {
            normalMessageDao = PersistentRepositoryFactory.getDao(NormalMessageDao.class);
        } catch (NullPointerException ex) {
            LOG.error("default message provider create error!", ex);
        } catch (ServerInnerException ex) {
            LOG.error("default message provider create error!", ex);
        }
    }

    @Override
    public List<NormalMessage> getOfflineMessages(String memberId) {
        LOG.debug("get not received message by to=" + memberId);
        List<NormalMessage> messages = normalMessageDao.getOfflineMessages(memberId);
        LOG.debug("get " + messages.size() + " messages!");
        return messages;
    }

    @Override
    public int setMessageForwarded(String memberId) {
        LOG.debug("set forwarded by to =" + memberId);
        int updateCount = normalMessageDao.setMessageForwarded(memberId);
        LOG.debug("set " + updateCount + " messages forwarded.");
        return updateCount;
    }
}
