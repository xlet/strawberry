package cn.w.im.persistent;

import cn.w.im.domains.member.BasicMember;
import cn.w.im.domains.member.WcnMember;

/**
 * w.cn member dao.
 */
public interface WcnMemberDao {

    /**
     * save w.cn member.
     *
     * @param member member.
     */
    void save(WcnMember member);

    BasicMember get(String memberId);
}
