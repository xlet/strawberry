package org.xlet.strawberry.core.actionSupport;

import org.xlet.strawberry.core.status.recentContact.RecentContactStatus;

import javax.swing.*;

/**
 * recent contact status item action.
 */
public class RecentContactStatusItemAction {

    private RecentContactStatus item;
    private Action action;

    public RecentContactStatusItemAction(RecentContactStatus recentContactStatus, ActionType actionType) {
        this.item = recentContactStatus;
        this.action = action;
    }

    public RecentContactStatus getItem() {
        return item;
    }

    public Action getAction() {
        return action;
    }

}
