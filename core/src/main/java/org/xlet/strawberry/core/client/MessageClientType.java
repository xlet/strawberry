package org.xlet.strawberry.core.client;

/**
 * Created by jackie on 14-6-17.
 * client type : Web,WinForm,Mobile.
 */
public enum MessageClientType {

    /**
     * Web Client.
     */
    Web(0x01),
    /**
     * WinForm Client.
     */
    WinForm(0x02),
    /**
     * Mobile Client.
     */
    Mobile(0x04);

    private int value;

    private MessageClientType(int value) {
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
}
