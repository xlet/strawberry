package cn.w.im.domains;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-19 下午8:03.
 * Summary: source type.
 */
public enum SourceType {

    /**
     * push.
     */
    Push(0x01),
    /**
     * Pull.
     */
    Pull(0x02);

    private int value;

    private SourceType(int value) {
        this.value = value;
    }

    /**
     * get enum value.
     *
     * @return value.
     */
    public int getValue() {
        return value;
    }
}
