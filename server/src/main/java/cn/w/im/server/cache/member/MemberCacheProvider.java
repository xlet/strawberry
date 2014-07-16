package cn.w.im.server.cache.member;

import cn.w.im.domains.basic.Member;
import cn.w.im.domains.client.MessageClientType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * member cache provider.
 */
public class MemberCacheProvider {

    private Map<String, CachedMember> cachedMemberMap = new ConcurrentHashMap<String, CachedMember>();

    private static final long EXPIRED_TIME = 60 * 20 * 1000;

    public boolean cacheExisted(String memberId) {
        if (cachedMemberMap.containsKey(memberId)) {
            CachedMember cacheMember = this.cachedMemberMap.get(memberId);
            if (!cacheMember.isExpired()) {
                return true;
            }
        }
        return false;
    }

    private CachedMember getCachedMember(String memberId) {
        return this.cachedMemberMap.get(memberId);
    }

    private void cacheAdd(Member member, MessageClientType clientType) {
        if (this.cachedMemberMap.containsKey(member.getId())) {
            this.cachedMemberMap.remove(member.getId());
        }
        this.cachedMemberMap.put(member.getId(), new CachedMember(member, clientType, EXPIRED_TIME));
    }
}
