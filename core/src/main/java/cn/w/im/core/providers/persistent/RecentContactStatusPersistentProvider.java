package cn.w.im.core.providers.persistent;

import cn.w.im.core.member.BasicMember;
import cn.w.im.core.member.relation.RecentContactStatus;

import java.util.Collection;

/**
 * recent contact status persistent provider interface.
 */
public interface RecentContactStatusPersistentProvider {

    /**
     * save statuses.
     *
     * @param statuses statuses.
     */
    void save(Collection<RecentContactStatus> statuses);

    /**
     * get statuses.
     *
     * @param owner statuses.
     * @param limit get limit.
     * @return statuses.
     */
    Collection<RecentContactStatus> get(BasicMember owner, int limit);
}
