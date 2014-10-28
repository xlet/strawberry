package cn.w.im.domains.member;

import cn.w.im.domains.MemberSourceType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.apache.commons.lang3.StringUtils;

/**
 * oa member.
 */
@JsonTypeName("oaMember")
public class OAMember extends BasicMember {

    private String post;

    private String department;

    public OAMember() {
        this.setMemberSource(MemberSourceType.OA);
        this.setTempMember(false);
        this.setInit(true);
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
}
