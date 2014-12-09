package org.xlet.strawberry.thirdparty.oa;

import org.xlet.strawberry.core.member.MemberSourceType;
import org.xlet.strawberry.core.member.BasicMember;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;

/**
 * oa member.
 */
public class OAMember extends BasicMember {

    private String post;

    private String department;

    public OAMember() {
        this.setMemberSource(MemberSourceType.OA);
        this.setTempMember(false);
        this.setInit(true);
        this.setOuter(true);
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @JsonIgnore
    public String getOrgCode() {
        if (!StringUtils.isEmpty(this.getId())) {
            int index = this.getId().indexOf(":");
            return this.getId().substring(0, index);
        }
        return "";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("post").append(this.getPost());
        sb.append("department").append(this.getDepartment());
        return sb.toString();
    }
}
