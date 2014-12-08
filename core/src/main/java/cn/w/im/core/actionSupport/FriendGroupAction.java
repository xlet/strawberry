package cn.w.im.core.actionSupport;

import cn.w.im.core.client.MessageClientConnectHistory;
import cn.w.im.core.exception.ServerInnerException;
import cn.w.im.core.member.BasicMember;
import cn.w.im.core.member.relation.FriendGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * group with action.
 * <p/>
 * this only for client get group changed information.
 */
public class FriendGroupAction {

    private FriendGroup friendGroup;
    private Map<String, ContactAction> contactActions;
    private ActionType action = ActionType.None;

    public FriendGroupAction(FriendGroup group, MessageClientConnectHistory connectHistory) throws ServerInnerException {
        this.friendGroup = group;
        //get friend group basic info action.
        FriendGroupChangeLog lastChangeLog = FriendGroupChangeLog.last(group, connectHistory.getConnectTime());
        if (lastChangeLog != null) {
            this.action = lastChangeLog.getAction();
        }
        //get contact add and delete action.
        this.contactActions = new HashMap<String, ContactAction>();
        Collection<FriendGroupContactChangeLog> contactChangeLogs =
                FriendGroupContactChangeLog.load(group, connectHistory.getConnectTime());
        for (FriendGroupContactChangeLog contactChangeLog : contactChangeLogs) {
            ContactAction contactAction = new ContactAction(contactChangeLog.getContact(), contactChangeLog.getAction());
            contactActions.put(contactChangeLog.getContact().getId(), contactAction);
        }
        //get contact basic info action.
        for (BasicMember contact : group.getContacts()) {
            if (!this.contactActions.containsKey(contact.getId())) {
                MemberInfoChangeLog memberInfoChangeLog = MemberInfoChangeLog.last(contact);
                if (memberInfoChangeLog != null) {
                    ContactAction contactAction = new ContactAction(contact, memberInfoChangeLog.getActionType());
                    this.contactActions.put(contact.getId(), contactAction);
                }
            }
        }
    }

    /**
     * get friend group.
     *
     * @return friend group.
     */
    public FriendGroup getFriendGroup() {
        return friendGroup;
    }

    /**
     * get member info actions.
     *
     * @return member info action.
     */
    public Collection<ContactAction> getContactActions() {
        return contactActions.values();
    }

    /**
     * get action.
     *
     * @return action.
     */
    public ActionType getAction() {
        return action;
    }
}
