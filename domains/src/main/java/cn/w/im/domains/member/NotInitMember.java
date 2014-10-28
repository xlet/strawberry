package cn.w.im.domains.member;

/**
 * not init member. do not load other property member.
 */
public class NotInitMember extends BasicMember {

    /**
     * constructor.
     * @param id member id.
     */
    public NotInitMember(String id) {
        this.setInit(false);
        this.setId(id);
    }
}
