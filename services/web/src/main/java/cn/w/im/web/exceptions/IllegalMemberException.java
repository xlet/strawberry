package cn.w.im.web.exceptions;

/**
 * @author jackie.
 *
 * illegal member exception.such as member not existed.etc.
 */
public class IllegalMemberException extends RuntimeException{

    public String name;

    /**
     * constructor.
     * @param fromId from id.
     */
    public IllegalMemberException(String fromId) {
        this.name = fromId;
    }

    /**
     * get name.
     * @return name.
     */
    public String getName() {
        return name;
    }
}
