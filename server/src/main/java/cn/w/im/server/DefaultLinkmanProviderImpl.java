package cn.w.im.server;

import cn.w.im.domains.basic.Member;
import cn.w.im.domains.basic.NearlyLinkman;
import cn.w.im.server.cache.member.MemberCacheProvider;

import java.util.List;

/**
 * default linkman provider.
 */
public class DefaultLinkmanProviderImpl implements LinkmanProvider {

    private MemberCacheProvider memberCacheProvider = new MemberCacheProvider();

    @Override
    public List<NearlyLinkman> getNearlyLinkmen(String memberId) {

        //todo : implement.
        return null;
    }

    @Override
    public Member getMember(String memberId) {

        //todo:implement.
        return null;
    }


}
