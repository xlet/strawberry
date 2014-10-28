package cn.w.im.exceptions;

/**
 * group empty exception.
 */
public class EmptyGroupException extends ServerInnerException {

    private String groupName;

    /**
     * constructor.
     */
    public EmptyGroupException(String groupName) {
        super("group[" + groupName + "] is empty!");
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }
}
