package org.xlet.strawberry.core.actionSupport;

import org.xlet.strawberry.core.member.BasicMember;

import java.util.ArrayList;
import java.util.Collection;

/**
 * recent contact status action.
 */
public class RecentContactStatusAction {

    private BasicMember owner;

    private Collection<RecentContactStatusItemAction> itemActions;

    public RecentContactStatusAction(BasicMember owner) {
        this.owner = owner;
        this.itemActions = new ArrayList<RecentContactStatusItemAction>();
    }

    public void addItemAction(RecentContactStatusItemAction itemAction) {
        this.itemActions.add(itemAction);
    }

    public BasicMember getOwner() {
        return owner;
    }

    public Collection<RecentContactStatusItemAction> getItemActions() {
        return itemActions;
    }
}
