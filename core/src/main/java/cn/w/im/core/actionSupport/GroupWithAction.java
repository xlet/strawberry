package cn.w.im.core.actionSupport;

import cn.w.im.core.ActionType;

import java.util.List;

/**
 * group with action.
 * <p/>
 * this only for client get group changed information.
 */
public class GroupWithAction {
    private String id;
    private String name;
    private List<MemberWithAction> members;
    private ActionType action;

    /**
     * get id.
     *
     * @return id.
     */
    public String getId() {
        return id;
    }

    /**
     * set id.
     *
     * @param id id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * get name.
     *
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * set name.
     *
     * @param name name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get member with action.
     *
     * @return member with action.
     */
    public List<MemberWithAction> getMembers() {
        return members;
    }

    /**
     * set member with action.
     *
     * @param members member with action.
     */
    public void setMembers(List<MemberWithAction> members) {
        this.members = members;
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
