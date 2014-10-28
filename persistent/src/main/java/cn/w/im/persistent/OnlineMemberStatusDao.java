package cn.w.im.persistent;

import cn.w.im.domains.OnlineMemberStatus;

/**
 * {@link cn.w.im.domains.OnlineMemberStatus} dao.
 */
public interface OnlineMemberStatusDao {

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
