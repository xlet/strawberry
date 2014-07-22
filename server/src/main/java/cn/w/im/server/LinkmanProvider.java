package cn.w.im.server;

import cn.w.im.domains.basic.Member;

import java.util.List;

/**
 * linkman provider.
 */
public interface LinkmanProvider {


    /**
     * get nearly linkmen.
     *
     * @param memberId member id.
     * @return nearly linkmen list.
     */
    List<Member> getNearlyLinkmen(String memberId);

    /**
     * get member info.
     *
     * @param memberId member id.
     * @return member info.
     */
    Member getMember(String memberId);

    List<Member> getMembers(List<String> ids);
}
