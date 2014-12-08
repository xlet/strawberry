package cn.w.im.core.actionSupport;

import cn.w.im.core.member.BasicMember;

/**
 * member info and action.
 * <p/>
 * this only for group with action.
 */
public class ContactAction {

    private BasicMember contact;

    private ActionType action;

    /**
     * default constructor.
     */
    public ContactAction() {
    }

    /**
     * constructor.
     *
     * @param contact contact.
     * @param action  action.
     */
    public ContactAction(BasicMember contact, ActionType action) {
        this.contact = contact;
        this.action = action;
    }

    /**
     * get contact.
     *
     * @return contact.
     */
    public BasicMember getContact() {
        return contact;
    }

    /**
     * set contact.
     *
     * @param contact contact.
     */
    public void setContact(BasicMember contact) {
        this.contact = contact;
    }

    /**
     * get action.
     *
     * @return action.
     */
    public ActionType getAction() {
        return action;
    }

    /**
     * set action.
     *
     * @param action action.
     */
    public void setAction(ActionType action) {
        this.action = action;
    }
}
