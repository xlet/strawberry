package cn.w.im.core.member;

/**
 * temp member.
 */
public class TempMember extends BasicMember {

    private String source;

    public TempMember() {
        this.setMemberSource(MemberSourceType.TEMP);
        this.setTempMember(true);
        this.setInit(true);
    }

    /**
     * get source.
     *
     * @return source.
     */
    public String getSource() {
        return source;
    }

    /**
     * set source.
     *
     * @param source source.
     */
    public void setSource(String source) {
        this.source = source;
    }
}
