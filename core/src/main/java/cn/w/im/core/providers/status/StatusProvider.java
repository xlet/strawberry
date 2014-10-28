package cn.w.im.core.providers.status;

import cn.w.im.domains.Status;

/**
 * member status provider.
 */
public interface StatusProvider {

    /**
     * get member status.
     * @param memberId member id.
     * @return member status.
     */
    Status status(String memberId);

}
