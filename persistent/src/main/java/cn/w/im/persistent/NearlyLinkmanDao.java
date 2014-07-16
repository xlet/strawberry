package cn.w.im.persistent;

import cn.w.im.domains.basic.NearlyLinkman;

import java.util.List;

/**
 * nearly linkman dao.
 */
public interface NearlyLinkmanDao {

    /**
     * get nearly linkman.
     *
     * @param memberId1 memberId1.
     * @param memberId2 memberId2.
     * @return {@link cn.w.im.domains.basic.NearlyLinkman}.
     */
    NearlyLinkman get(String memberId1, String memberId2);

    /**
     * get nearly linkmen.
     *
     * @param memberId  memberId.
     * @param pageIndex page index.
     * @param pageSize  page size.
     * @return collection of {@link cn.w.im.domains.basic.NearlyLinkman}.
     */
    List<NearlyLinkman> get(String memberId, int pageSize, int pageIndex);

    /**
     * save {@link cn.w.im.domains.basic.NearlyLinkman}.
     *
     * @param nearlyLinkman {@link cn.w.im.domains.basic.NearlyLinkman}.
     */
    void save(NearlyLinkman nearlyLinkman);

    /**
     * update last link time to current time.
     *
     * @param nearlyLinkman updated nearlyLinkman object.
     * @return updated count.
     */
    int updateLinkTime(NearlyLinkman nearlyLinkman);
}
