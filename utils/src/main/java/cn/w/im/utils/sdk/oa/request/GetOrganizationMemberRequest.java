package cn.w.im.utils.sdk.oa.request;

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
