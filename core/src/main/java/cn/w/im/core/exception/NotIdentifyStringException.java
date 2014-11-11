package cn.w.im.core.exception;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-27 下午5:54.
 * Summary: the string is not identified.
 */
public class NotIdentifyStringException extends Exception {

    private String allowedStrings;

    private String currentString;

    /**
     * constructor.
     *
     * @param currentString  current string.
     * @param allowedStrings allowed strings.
     */
    public NotIdentifyStringException(String currentString, String allowedStrings) {
        super("the string[" + currentString + "is not identified.suggest " + allowedStrings);
    }

    /**
     * get allowed strings.
     *
     * @return allowed strings.
     */
    public String getAllowedStrings() {
        return allowedStrings;
    }

    /**
     * get current string.
     *
     * @return current string.
     */
    public String getCurrentString() {
        return currentString;
    }
}
