package cn.w.im.web.services;

import cn.w.im.domains.Status;
import cn.w.im.web.vo.LinkmanViewObject;
import cn.w.im.web.vo.MemberViewObject;

/**
 * @author jackie.
 */
public interface MemberService {
    /**
     * get whether the member existed.
     *
     * @param memberId member id.
     * @param referrer referrer url.
     * @return {@code true} if the member existed.
     */
    boolean existed(String memberId, String referrer);

    /**
     * create temp member.
     *
     * @param referrer referrer url.
     * @return temp member. see {@link cn.w.im.web.vo.LinkmanViewObject}
     */
    LinkmanViewObject createTemp(String referrer);

    /**
     * get member info.
     *
     * @param memberId member id.
     * @param referrer referrer url.
     * @return member info. see {@link cn.w.im.web.vo.MemberViewObject}
     */
    MemberViewObject get(String memberId, String referrer);

    /**
     * set member's status online.
     *
     * @param loginId  login id.
     * @param referrer referrer url.
     */
    void online(String loginId, String referrer);

    /**
     * get member status.
     *
     * @param memberId member id.
     * @return {@link Status} of member.
     */
    Status getStatus(String memberId, String referrer);
}
