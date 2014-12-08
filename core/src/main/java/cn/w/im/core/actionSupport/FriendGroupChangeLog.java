package cn.w.im.core.actionSupport;

import cn.w.im.core.exception.ServerInnerException;
import cn.w.im.core.member.BasicMember;
import cn.w.im.core.member.relation.FriendGroup;
import cn.w.im.core.persistent.NotSupportedDataStoreException;
import cn.w.im.core.persistent.PersistentProviderFactory;

import java.util.Collection;

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
