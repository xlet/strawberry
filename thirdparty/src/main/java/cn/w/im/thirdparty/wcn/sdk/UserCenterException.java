package cn.w.im.thirdparty.wcn.sdk;

import cn.w.im.thirdparty.wcn.sdk.model.Response;

/**
 * Creator: JimmyLin
 * DateTime: 14-6-30 上午11:18
 * Summary: 用户中心异常
 */
public class UserCenterException extends Exception {

    public UserCenterException() {
    }

    public UserCenterException(String message) {
        super(message);
    }


    public UserCenterException(Response response) {
        super(response.getErrorMessage());
    }
}
