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
        this.setOuter(false);
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("source").append(this.getSource());
        return sb.toString();
    }
}
