package cn.w.im.domains.member;

import cn.w.im.domains.MemberSourceType;

/**
 * temp member.
 */
public class TempMember extends BasicMember {

    private String source;

    public TempMember() {
        this.setMemberSource(MemberSourceType.Temp);
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
