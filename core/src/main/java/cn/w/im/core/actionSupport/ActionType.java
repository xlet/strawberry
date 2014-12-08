package cn.w.im.core.actionSupport;

/**
 * Action type.
 */
public enum ActionType {

    None(0x01),
    Add(0x02),
    Update(0x04),
    Delete(0x08);

    private int value;

    ActionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
