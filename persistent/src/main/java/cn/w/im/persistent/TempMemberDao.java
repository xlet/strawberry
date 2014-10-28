package cn.w.im.persistent;

import cn.w.im.domains.member.TempMember;

/**
 * temp member dao.
 */
public interface TempMemberDao {

    /**
     * save temp member.
     * @param tempMember temp member.
     */
    void save(TempMember tempMember);

    /**
     * get last temp member.
     *
     * @return last temp member.
     */
    TempMember getLast();

    /**
     * get temp member by name.
     *
     * @param memberId member id.
     * @return temp member.
     */
    TempMember get(String memberId);
}
