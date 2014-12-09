package org.xlet.strawberry.core.actionSupport;

import org.xlet.strawberry.core.client.MessageClient;
import org.xlet.strawberry.core.client.MessageClientConnectHistory;
import org.xlet.strawberry.core.exception.ServerInnerException;
import org.xlet.strawberry.core.member.relation.FriendGroup;
import org.xlet.strawberry.core.status.memberAll.MemberAll;
import org.xlet.strawberry.core.status.recentContact.RecentContactStatus;
import org.xlet.strawberry.core.status.recentContact.RecentContactStatuses;

import java.util.ArrayList;
import java.util.Collection;

/**
 * member version.
 */
public class MemberVersion {

    private FriendGroupActionPersistentProvider friendGroupActionPersistentProvider;

    private MemberAll self;

    private MessageClientConnectHistory lastConnectHistory;

    public MemberVersion(MemberAll memberAll, MessageClient client) throws ServerInnerException {
        this.self = memberAll;
        this.lastConnectHistory = MessageClientConnectHistory.last(client);
    }

    private Collection<FriendGroupAction> getFriendGroupAction() throws ServerInnerException {
        Collection<FriendGroup> ownerFriendGroups = this.self.friendGroups();
        Collection<FriendGroupAction> friendGroupActions = new ArrayList<FriendGroupAction>();
        for (FriendGroup friendGroup : ownerFriendGroups) {
            FriendGroupAction friendGroupAction = new FriendGroupAction(friendGroup, this.lastConnectHistory);
            friendGroupActions.add(friendGroupAction);
        }
        return friendGroupActions;
    }

    private RecentContactStatusAction getRecnetContactStatusAction() throws ServerInnerException {
        RecentContactStatusAction recentContactStatusAction = new RecentContactStatusAction(this.self.self());
        RecentContactStatuses contactStatuses = this.self.recentContacts();
        for (RecentContactStatus contactStatus : contactStatuses.getContactStatuses()) {
            if (contactStatus.getLastContactTime() > lastConnectHistory.getConnectTime()) {
                RecentContactStatusItemAction itemAction = new RecentContactStatusItemAction(contactStatus, ActionType.Add);
                recentContactStatusAction.addItemAction(itemAction);
            }
        }
        return recentContactStatusAction;
    }
}
