package cn.w.im.thirdparty.oa.sdk.response;

import cn.w.im.thirdparty.oa.sdk.domain.OaMemberProfile;

/**
 * verify response.
 */
public class VerifyResponse extends BaseResponse {

    private OaMemberProfile userInfo;

    public OaMemberProfile getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(OaMemberProfile userInfo) {
        this.userInfo = userInfo;
    }
}
