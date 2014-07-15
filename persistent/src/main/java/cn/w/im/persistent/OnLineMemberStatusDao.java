package cn.w.im.persistent;

import cn.w.im.domains.common.OnlineMemberStatus;

/**
 * {@link cn.w.im.domains.common.OnlineMemberStatus} dao.
 */
public interface OnLineMemberStatusDao {

    /**
     * save member status.
     *
     * @param memberStatus member status.
     */
    void save(OnlineMemberStatus memberStatus);

    /**
     * delete member status.
     *
     * @param loginId login id.
     */
    void delete(String loginId);

    /**
     * get member status.
     *
     * @param loginId login id.
     * @return member status.
     */
    OnlineMemberStatus get(String loginId);
}
