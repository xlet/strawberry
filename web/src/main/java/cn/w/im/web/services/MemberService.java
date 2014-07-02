package cn.w.im.web.services;

import cn.w.im.utils.sdk.usercenter.UserCenterException;
import cn.w.im.web.vo.LinkmanViewObject;
import cn.w.im.web.vo.MemberViewObject;

/**
 * @author jackie.
 */
public interface MemberService {
    /**
     * get whether the member existed.
     *
     * @param fromId   from id.
     * @param referrer referrer url.
     * @return {@code true} if the member existed.
     */
    boolean existed(String fromId, String referrer);

    /**
     * create temp member.
     * @param referrer referrer url.
     * @return temp member. see {@link cn.w.im.web.vo.LinkmanViewObject}
     */
    LinkmanViewObject createTemp(String referrer);

    /**
     * get member info.
     * @param fromId  from id.
     * @param referrer referrer url.
     * @return member info. see {@link cn.w.im.web.vo.MemberViewObject}
     */
    MemberViewObject get(String fromId, String referrer);
}
