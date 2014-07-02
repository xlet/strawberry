package cn.w.im.utils.sdk.usercenter;

import cn.w.im.utils.sdk.usercenter.model.Account;
import cn.w.im.utils.sdk.usercenter.model.MemberProfile;
import cn.w.im.utils.sdk.usercenter.model.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Creator: JimmyLin
 * DateTime: 14-6-30 上午9:59
 * Summary:
 */
public class MemberService extends UserCenterSupport implements Members {

    /**
     * 通过w号获取会员资料
     *
     * @param wid
     * @return 会员资料
     */
    @Override
    public MemberProfile getByWid(String wid) throws UserCenterException {
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
        String url = config.getBaseUrl() + "member/" + account.getWid() + "/verify";
        Map<String, String> params = new HashMap<String, String>();
        params.put("password", MD5Util.twiceMd5(account.getPassword()));
        Response response = post(url, params, Response.class);
        return response.isSuccess();
    }
}
