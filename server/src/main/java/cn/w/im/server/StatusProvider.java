package cn.w.im.server;

import cn.w.im.domains.basic.Member;
import cn.w.im.domains.basic.Status;

import java.util.List;

/**
 * member status provider.
 */
public interface StatusProvider {

    /**
     * render online status to each member
     *
     * @param members
     */
    void render(List<Member> members);

    Status status(String loginId);

}
