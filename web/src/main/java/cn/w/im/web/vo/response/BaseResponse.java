package cn.w.im.web.vo.response;

/**
 * Created by jackie on 14-6-23.
 */
public class BaseResponse {
    private boolean success;
    private int errorCode;
    private String errorMessage;
    private String time;
    private String token;

    /**
     * get whether request success.
     * @return {@code true} if request success.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * set whether request success.
     * @param success {@code true} if request success.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * get error code.
     * @return error code.
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * set error code.
     * @param errorCode error code.
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * get error message.
     * @return error message.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * set error message.
     * @param errorMessage error message.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * get response time.
     * @return response time.
     */
    public String getTime() {
        return time;
    }

    /**
     * set response time.
     * @param time response time.
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * get token.
     * @return token.
     */
    public String getToken() {
        return token;
    }

    /**
     * set token.
     * @param token token.
     */
    public void setToken(String token) {
        this.token = token;
    }
}
