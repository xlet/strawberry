package cn.w.im.core.actionSupport;

import cn.w.im.core.client.MessageClient;
import cn.w.im.core.client.MessageClientConnectHistory;
import cn.w.im.core.exception.ServerInnerException;
import cn.w.im.core.member.BasicMember;
import cn.w.im.core.member.relation.FriendGroup;
import cn.w.im.core.persistent.NotSupportedDataStoreException;
import cn.w.im.core.persistent.PersistentProviderFactory;
import cn.w.im.core.spring.SpringContext;
import cn.w.im.core.status.memberAll.MemberAll;
import cn.w.im.core.status.recentContact.RecentContactStatus;
import cn.w.im.core.status.recentContact.RecentContactStatuses;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
