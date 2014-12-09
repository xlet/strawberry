package org.xlet.strawberry.core.actionSupport;

import org.xlet.strawberry.core.member.relation.FriendGroup;

import java.util.Collection;

/**
 * friend group action persistent provider.
 */
public interface FriendGroupActionPersistentProvider {

    /**
     * save change log.
     *
     * @param changeLog change log.
     */
    void save(FriendGroupChangeLog changeLog);

    void save(FriendGroupContactChangeLog contactChangeLog);

    Collection<FriendGroupContactChangeLog> loadContactLogs(FriendGroup group, long lastSyncTime);

    /**
     * get last friend group change log.
     *
     * @param group           group.
     * @param lastConnectTime last connect time.
     * @return last friend group change.
     */
    FriendGroupChangeLog last(FriendGroup group, long lastConnectTime);
}
