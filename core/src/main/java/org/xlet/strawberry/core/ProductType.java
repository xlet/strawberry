package org.xlet.strawberry.core;

/**
 * product type.
 */
public enum ProductType {

    /**
     * for oa.
     */
    OA(0x01),
    /**
     * for xlet.cn
     */
    WCN(0x02),
    /**
     * for other.
     */
    OTHER(0x04);

    private int value;

    ProductType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
