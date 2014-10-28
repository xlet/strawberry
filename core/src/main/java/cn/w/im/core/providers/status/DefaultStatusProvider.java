package cn.w.im.core.providers.status;

import cn.w.im.domains.Status;
import cn.w.im.exceptions.NotSupportedDataStoreException;
import cn.w.im.persistent.OnlineMemberStatusDao;
import cn.w.im.persistent.PersistentRepositoryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * default implement of {@link cn.w.im.core.providers.status.StatusProvider}.
 */
public class DefaultStatusProvider implements StatusProvider {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private OnlineMemberStatusDao memberStatusDao;

    public DefaultStatusProvider() {
        try {
            this.memberStatusDao = PersistentRepositoryFactory.getDao(OnlineMemberStatusDao.class);
        } catch (NotSupportedDataStoreException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public Status status(String memberId) {
        return memberStatusDao.get(memberId).getStatus();
    }


}
