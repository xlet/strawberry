package cn.w.im.core.status;

import cn.w.im.core.status.StatusProvider;
import cn.w.im.domains.basic.Member;
import cn.w.im.domains.basic.OnlineMemberStatus;
import cn.w.im.domains.basic.Status;
import cn.w.im.exceptions.NotSupportedDataStoreException;
import cn.w.im.persistent.OnlineMemberStatusDao;
import cn.w.im.persistent.PersistentRepositoryFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * default implement of {@link cn.w.im.core.status.StatusProvider}.
 */
public class DefaultStatusProvider implements StatusProvider {
    private Log log = LogFactory.getLog(this.getClass());

    private OnlineMemberStatusDao memberStatusDao;

    public DefaultStatusProvider() {
        try {
            this.memberStatusDao = PersistentRepositoryFactory.getDao(OnlineMemberStatusDao.class);
        } catch (NotSupportedDataStoreException e) {
            log.error(e);
        }
    }

    @Override
    public void render(List<Member> members) {
            if (members != null && members.size() > 0) {
                for (Member member : members) {
                    OnlineMemberStatus memberStatus = memberStatusDao.get(member.getId());
                    if (memberStatus != null) {
                        member.setStatus(memberStatus.getStatus().getValue());
                    }
                }
            }

    }

    @Override
    public Status status(String loginId) {
        return memberStatusDao.get(loginId).getStatus();
    }


}
