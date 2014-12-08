package cn.w.im.core.member;

/**
 * not init member. do not load other property member.
 * <p/>
 * use by outer member.
 */
public class NotInitMember extends BasicMember {

    private String version;

    /**
     * constructor.
     *
     * @param id member id.
     */
    public NotInitMember(String id, String version) {
        this.setInit(false);
        this.setId(id);
        this.setOuter(true);
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    protected void setVersion(String version) {
        this.version = version;
    }
}
