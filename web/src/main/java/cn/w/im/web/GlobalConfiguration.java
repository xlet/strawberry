package cn.w.im.web;

/**
 * global configuration.
 */
public class GlobalConfiguration {

    private String defaultThumb;
    private String messageServerName;
    private int messageServerPort;

    /**
     * get default thumb.
     *
     * @return default thumb.
     */
    public String getDefaultThumb() {
        return defaultThumb;
    }

    /**
     * set default thumb.
     *
     * @param defaultThumb default thumb.
     */
    public void setDefaultThumb(String defaultThumb) {
        this.defaultThumb = defaultThumb;
    }

    /**
     * get message server name.
     * @return message server name.
     */
    public String getMessageServerName() {
        return messageServerName;
    }

    /**
     * set message server name.
     * @param messageServerName
     */
    public void setMessageServerName(String messageServerName) {
        this.messageServerName = messageServerName;
    }

    /**
     * get message server port.
     * @return message server port.
     */
    public int getMessageServerPort() {
        return messageServerPort;
    }

    /**
     * set message server port.
     * @param messageServerPort message server port.
     */
    public void setMessageServerPort(int messageServerPort) {
        this.messageServerPort = messageServerPort;
    }
}
