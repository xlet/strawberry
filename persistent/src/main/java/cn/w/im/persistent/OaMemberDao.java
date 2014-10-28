package cn.w.im.persistent;

import cn.w.im.domains.member.OAMember;

/**
 * oa member dao.
 */
public interface OaMemberDao {

    void save(OAMember member);

    OAMember get(String memberId);
}
