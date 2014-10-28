package cn.w.im.utils.sdk.oa.response;

import cn.w.im.utils.sdk.oa.domain.OaMemberProfile;

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
