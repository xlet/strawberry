package cn.w.im.thirdparty.wcn.sdk.model;

import java.util.Map;

/**
 * Creator: JimmyLin
 * DateTime: 14-6-30 上午9:51
 * Summary:
 */
public class Response {

    private boolean success;

    private int errorCode;

    private String errorMessage;

    private Map<String, String> errors;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
