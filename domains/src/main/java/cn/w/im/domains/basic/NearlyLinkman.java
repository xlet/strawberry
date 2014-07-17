package cn.w.im.domains.basic;

/**
 * nearly linkman.
 */
public class NearlyLinkman {

    private String memberId1, memberId2;
    private long lastLinkTime;

    public NearlyLinkman() {
    }

    public NearlyLinkman(String memberId1, String memberId2) {
        this.memberId1 = memberId1;
        this.memberId2 = memberId2;
        this.lastLinkTime = System.currentTimeMillis();
    }

    public String toggle(String other) {
        return other.equals(memberId1) ? memberId2 : memberId1;
    }

    public String getMemberId1() {
        return memberId1;
    }

    public void setMemberId1(String memberId1) {
        this.memberId1 = memberId1;
    }

    public String getMemberId2() {
        return memberId2;
    }

    public void setMemberId2(String memberId2) {
        this.memberId2 = memberId2;
    }

    public long getLastLinkTime() {
        return lastLinkTime;
    }

    public void setLastLinkTime(long lastLinkTime) {
        this.lastLinkTime = lastLinkTime;
    }
}
