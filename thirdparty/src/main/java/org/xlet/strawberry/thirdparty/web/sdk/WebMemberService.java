package org.xlet.strawberry.thirdparty.web.sdk;

import org.xlet.strawberry.thirdparty.web.sdk.model.Account;
import org.xlet.strawberry.thirdparty.web.sdk.model.MemberProfile;

/**
 * Creator: JimmyLin
 * DateTime: 14-6-30 下午1:40
 * Summary:
 */
public interface WebMemberService {

    MemberProfile getByWid(String wid) throws UserCenterException;

    boolean verify(Account account) throws UserCenterException;
}
