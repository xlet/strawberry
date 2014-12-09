package org.xlet.strawberry.thirdparty.oa.sdk.response;

import org.xlet.strawberry.thirdparty.oa.sdk.domain.OaMemberProfile;

import java.util.List;

/**
 * get organization member response.
 */
public class GetOrganizationMemberResponse extends BaseResponse {

    private List<OaMemberProfile> users;

    public List<OaMemberProfile> getUsers() {
        return users;
    }

    public void setUsers(List<OaMemberProfile> users) {
        this.users = users;
    }
}
