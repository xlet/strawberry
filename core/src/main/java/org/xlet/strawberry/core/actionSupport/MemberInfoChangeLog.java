package org.xlet.strawberry.core.actionSupport;

import org.xlet.strawberry.core.exception.ServerInnerException;
import org.xlet.strawberry.core.member.BasicMember;
import org.xlet.strawberry.core.persistent.PersistentProviderFactory;

/**
 * member info change log.
 */
public class MemberInfoChangeLog {

    private BasicMember member;

    private ActionType actionType;

    private long changedTime;

    public MemberInfoChangeLog(BasicMember member, ActionType actionType, long changedTime) {
        this.member = member;
        this.actionType = actionType;
        this.changedTime = changedTime;
    }

    public MemberInfoChangeLog(BasicMember member, ActionType actionType) {
        this(member, actionType, System.currentTimeMillis());
    }

    public BasicMember getMember() {
        return member;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public long getChangedTime() {
        return changedTime;
    }

    public void save() throws ServerInnerException {
        PersistentProviderFactory.getPersistentProvider(MemberInfoActionPersistentProvider.class).save(this);
    }

    public static MemberInfoChangeLog last(BasicMember member) throws ServerInnerException {
        return PersistentProviderFactory.getPersistentProvider(MemberInfoActionPersistentProvider.class).last(member);
    }
}
