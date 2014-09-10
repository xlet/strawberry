package cn.w.im.web.services;

import cn.w.im.web.vo.LinkmanViewObject;

import java.util.List;

/**
 * nearly linkman service.
 */
public interface NearlyLinkmanService {

    /**
     * get nearly linkman.
     * @param loginId login id.
     * @param referrer referrer.
     * @param pageSize page size.
     * @param pageIndex page index.
     * @return pagination nearly linkmen.
     */
    List<LinkmanViewObject> get(String loginId, String referrer,int pageSize,int pageIndex);
}
