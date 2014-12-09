package org.xlet.strawberry.core.status.basicStatus;

/**
 * status.
 */
public enum Status {

    /**
     * online.
     */
    Online(0x01),

    /**
     * offline.
     */
    Offline(0x02),

    /**
     * leave.
     */
    Leave(0x04),

    /**
     * busy.
     */
    Busy(0x08),

    /**
     * do not disturb.
     */
    DoNotDisturb(0x10),

    /**
     * hiding.
     */
    Hiding(0x20);

    private int value;

    private Status(int value) {
        this.value = value;
    }

    /**
     * get value.
     *
     * @return value.
     */
    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    /**
     * create Status by value.
     *
     * @param value value.
     * @return {see Status.}
     */
    public static Status valueOf(int value) {
        switch (value) {
            case 0x01:
                return Status.Online;
            case 0x02:
                return Status.Offline;
            case 0x04:
                return Status.Leave;
            case 0x08:
                return Status.Busy;
            case 0x10:
                return Status.DoNotDisturb;
            case 0x20:
                return Status.Hiding;
            default:
                return Status.Offline;
        }
    }
}
