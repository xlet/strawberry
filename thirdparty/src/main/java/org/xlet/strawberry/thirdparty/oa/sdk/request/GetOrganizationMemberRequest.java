package org.xlet.strawberry.thirdparty.oa.sdk.request;

/**
 * get organization member request.
 */
public class GetOrganizationMemberRequest {

    private String orgCode;

    public GetOrganizationMemberRequest(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}
