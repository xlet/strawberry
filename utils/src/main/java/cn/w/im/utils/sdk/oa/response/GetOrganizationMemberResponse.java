package cn.w.im.utils.sdk.oa.response;

import cn.w.im.utils.sdk.oa.domain.OaMemberProfile;

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
