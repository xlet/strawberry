package org.xlet.strawberry.core.member;

/**
 * sex type.
 */
public enum SexType {
    Male(0x01),
    Female(0x02);

    private int value;

    SexType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
