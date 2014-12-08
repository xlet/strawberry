package cn.w.im.core.member;

/**
 * member type.
 */
public enum MemberSourceType {

    /**
     * member of w.cn.
     */
    WCN(0x01),
    /**
     * member of oa.
     */
    OA(0x02),
    /**
     * member of temp.
     */
    TEMP(0x04);

    private int value;

    MemberSourceType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
