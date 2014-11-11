package cn.w.im.thirdparty.wcn.sdk;

import cn.w.im.thirdparty.wcn.sdk.model.Account;
import cn.w.im.thirdparty.wcn.sdk.model.MemberProfile;

/**
 * Creator: JimmyLin
 * DateTime: 14-6-30 下午1:40
 * Summary:
 */
public interface WcnMemberService {

    MemberProfile getByWid(String wid) throws UserCenterException;

    boolean verify(Account account) throws UserCenterException;
}
