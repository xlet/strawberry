package cn.w.im.core.actionSupport;

import cn.w.im.core.exception.ServerInnerException;
import cn.w.im.core.member.BasicMember;
import cn.w.im.core.member.relation.FriendGroup;
import cn.w.im.core.persistent.NotSupportedDataStoreException;
import cn.w.im.core.persistent.PersistentProviderFactory;

import java.util.Collection;

/**
 * friend group contact change log.
 */
public class FriendGroupContactChangeLog {

    private BasicMember contact;

    private ActionType action;

    private long changedTime;

    public FriendGroupContactChangeLog(BasicMember contact, ActionType action) {
        this(contact, action, System.currentTimeMillis());
    }

    private FriendGroupContactChangeLog(BasicMember contact, ActionType action, long changedTime) {
        this.contact = contact;
        this.action = action;
        this.changedTime = changedTime;
    }

    public BasicMember getContact() {
        return contact;
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

    public static Collection<FriendGroupContactChangeLog> load(FriendGroup group, long lastConnectTime) throws NotSupportedDataStoreException {
        return getPersistentProvider().loadContactLogs(group, lastConnectTime);
    }
}
