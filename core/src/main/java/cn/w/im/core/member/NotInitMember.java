package cn.w.im.core.member;

/**
 * not init member. do not load other property member.
 */
public class NotInitMember extends BasicMember {

    /**
     * constructor.
     *
     * @param id member id.
     */
    public NotInitMember(String id) {
        this.setInit(false);
        this.setId(id);
    }
}
