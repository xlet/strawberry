package org.xlet.strawberry.core.actionSupport;

import org.xlet.strawberry.core.exception.ServerInnerException;
import org.xlet.strawberry.core.member.relation.FriendGroup;
import org.xlet.strawberry.core.persistent.NotSupportedDataStoreException;
import org.xlet.strawberry.core.persistent.PersistentProviderFactory;

/**
 * friend group change log.
 */
public class FriendGroupChangeLog {

    private FriendGroup friendGroup;

    private ActionType action;

    private long changedTime;

    public FriendGroupChangeLog(FriendGroup friendGroup, ActionType action) {
        this(friendGroup, action, System.currentTimeMillis());
    }

    public FriendGroupChangeLog(FriendGroup friendGroup, ActionType action, long changedTime) {
        this.friendGroup = friendGroup;
        this.action = action;
        this.changedTime = changedTime;
    }

    public FriendGroup getFriendGroup() {
        return friendGroup;
    }

    public ActionType getAction() {
        return action;
    }

    public long getChangedTime() {
        return changedTime;
    }

    public void save() throws ServerInnerException {
        getPersistentProvider().save(this);
    }

    private static FriendGroupActionPersistentProvider getPersistentProvider() throws NotSupportedDataStoreException {
        return PersistentProviderFactory.getPersistentProvider(FriendGroupActionPersistentProvider.class);
    }

    public static FriendGroupChangeLog last(FriendGroup group, long connectTime) throws ServerInnerException {
        return getPersistentProvider().last(group, connectTime);
    }
}
