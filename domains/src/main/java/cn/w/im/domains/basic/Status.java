package cn.w.im.domains.basic;

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
    Offline(0x02);

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
            case 1:
                return Status.Online;
            case 2:
                return Status.Offline;
            default:
                return Status.Offline;
        }
    }
}