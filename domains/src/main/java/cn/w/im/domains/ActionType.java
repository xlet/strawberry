package cn.w.im.domains;

/**
 * Action type.
 */
public enum ActionType {

    Add(0x01),
    Update(0x02),
    Delete(0x04);

    private int value;

    ActionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
