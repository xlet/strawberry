package cn.w.im.exceptions;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-27 上午11:08.
 * Summary: occur when listening thread start error.
 */
public class ListeningThreadStartErrorException extends Exception {

    /**
     * constructor.
     */
    public ListeningThreadStartErrorException() {
        super("the listening thread perhaps start error.");
    }
}
