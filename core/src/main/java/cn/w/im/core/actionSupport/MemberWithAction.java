package cn.w.im.core.actionSupport;

import cn.w.im.core.member.BasicMember;

/**
 * member info and action.
 * <p/>
 * this only for group with action.
 */
public class MemberWithAction {

    private BasicMember member;

    private ActionType action;

    /**
     * default constructor.
     */
    public MemberWithAction() {
    }

    /**
     * constructor.
     *
     * @param member member.
     * @param action action.
     */
    public MemberWithAction(BasicMember member, ActionType action) {
        this.member = member;
        this.action = action;
    }

    /**
     * get member.
     *
     * @return member.
     */
    public BasicMember getMember() {
        return member;
    }

    /**
     * set member.
     *
     * @param member member.
     */
    public void setMember(BasicMember member) {
        this.member = member;
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
