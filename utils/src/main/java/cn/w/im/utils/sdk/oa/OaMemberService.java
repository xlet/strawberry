package cn.w.im.utils.sdk.oa;

import cn.w.im.utils.sdk.oa.request.GetOrganizationMemberRequest;
import cn.w.im.utils.sdk.oa.request.VerifyRequest;
import cn.w.im.utils.sdk.oa.response.GetOrganizationMemberResponse;
import cn.w.im.utils.sdk.oa.response.VerifyResponse;

/**
 *
 */
public interface OaMemberService {

    VerifyResponse verify(VerifyRequest request) throws Exception;

    GetOrganizationMemberResponse getMembers(GetOrganizationMemberRequest request) throws Exception;
}
