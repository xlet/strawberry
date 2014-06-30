package cn.w.im.utils.sdk.usercenter;

import cn.w.im.utils.sdk.usercenter.model.Response;

/**
 * Creator: JimmyLin
 * DateTime: 14-6-30 上午11:18
 * Summary:
 */
public class UcException extends Exception {

    public UcException() {
    }

    public UcException(String message) {
        super(message);
    }


    public UcException(Response response) {
        super(response.getErrorMessage());
    }
}
