package org.xlet.strawberry.core.status.recentContact;

import org.xlet.strawberry.core.member.BasicMember;

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
