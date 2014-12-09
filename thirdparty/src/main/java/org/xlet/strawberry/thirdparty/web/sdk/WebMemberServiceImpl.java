package org.xlet.strawberry.thirdparty.web.sdk;

import org.xlet.strawberry.thirdparty.web.sdk.model.Account;
import org.xlet.strawberry.thirdparty.web.sdk.model.MemberProfile;
import org.xlet.strawberry.thirdparty.web.sdk.model.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Creator: JimmyLin
 * DateTime: 14-6-30 上午9:59
 * Summary:
 */
public class WebMemberServiceImpl extends UserCenterSupport implements WebMemberService {

    /**
     * 通过w号获取会员资料
     *
     * @param wid
     * @return 会员资料
     */
    @Override
    public MemberProfile getByWid(String wid) throws UserCenterException {
        checkWid(wid);
        String url = config.getBaseUrl() + "member/" + wid;
        return get(url, MemberProfile.class);
    }

    /**
     * 登录校验
     *
     * @param account
     * @return 校验成功与否
     */
    @Override
    public boolean verify(Account account) throws UserCenterException {
        checkWid(account.getWid());
        String url = config.getBaseUrl() + "member/" + account.getWid() + "/verify";
        Map<String, String> params = new HashMap<String, String>();
        params.put("password", MD5Util.twiceMd5(account.getPassword()));
        Response response = post(url, params, Response.class);
        return response.isSuccess();
    }
}
