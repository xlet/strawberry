package cn.w.im.thirdparty.oa.sdk;

import cn.w.im.thirdparty.oa.sdk.request.GetOrganizationMemberRequest;
import cn.w.im.thirdparty.oa.sdk.request.VerifyRequest;
import cn.w.im.thirdparty.oa.sdk.response.GetOrganizationMemberResponse;
import cn.w.im.thirdparty.oa.sdk.response.VerifyResponse;

/**
 *
 */
public interface OaMemberService {

    VerifyResponse verify(VerifyRequest request) throws Exception;

    GetOrganizationMemberResponse getMembers(GetOrganizationMemberRequest request) throws Exception;
}
