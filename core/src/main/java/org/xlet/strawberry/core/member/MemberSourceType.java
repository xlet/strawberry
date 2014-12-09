package org.xlet.strawberry.core.member;

/**
 * member type.
 */
public enum MemberSourceType {

    /**
     * member of xlet.cn.
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
