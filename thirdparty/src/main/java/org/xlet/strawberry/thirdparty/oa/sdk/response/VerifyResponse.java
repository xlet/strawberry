package org.xlet.strawberry.thirdparty.oa.sdk.response;

import org.xlet.strawberry.thirdparty.oa.sdk.domain.OaMemberProfile;

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
