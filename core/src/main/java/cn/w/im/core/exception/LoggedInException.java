package cn.w.im.core.exception;

import cn.w.im.core.ErrorCodeDefine;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-21 上午10:53.
 * Summary: user logged in other place error.
 */
public class LoggedInException extends MessageClientException {

    private String otherPlace;

    /**
     * constructor.
     *
     * @param otherPlace logged in place.(province,city)
     */
    public LoggedInException(String otherPlace) {
        super("logged in.", ErrorCodeDefine.LOGGEDINERRORCODE);
        this.otherPlace = otherPlace;
    }

    /**
     * get other logged in place.
     *
     * @return other logged in place.
     */
    public String getOtherPlace() {
        return otherPlace;
    }
}
