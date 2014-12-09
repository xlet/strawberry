package org.xlet.strawberry.thirdparty.oa.sdk;

import org.xlet.strawberry.thirdparty.oa.sdk.request.GetOrganizationMemberRequest;
import org.xlet.strawberry.thirdparty.oa.sdk.request.VerifyRequest;
import org.xlet.strawberry.thirdparty.oa.sdk.response.GetOrganizationMemberResponse;
import org.xlet.strawberry.thirdparty.oa.sdk.response.VerifyResponse;

/**
 *
 */
public interface OaMemberService {

    VerifyResponse verify(VerifyRequest request) throws Exception;

    GetOrganizationMemberResponse getMembers(GetOrganizationMemberRequest request) throws Exception;
}
