package cn.w.im.core.cache.member;

import cn.w.im.domains.basic.Member;
import cn.w.im.domains.client.MessageClientType;

import java.util.Map;
import java.util.concurrent.*;

/**
 * member cache provider.
 */
public class MemberCacheProvider {

    private final Map<String, CachedMember> cachedMemberMap = new ConcurrentHashMap<String, CachedMember>();

    private static final long EXPIRED_TIME = 60 * 20 * 1000;

    private ScheduledFuture scheduledFuture;

    private final ScheduledExecutorService executor;

    private long interval = 1;


    public MemberCacheProvider() {
        this.executor = new ScheduledThreadPoolExecutor(1);
        this.scheduledFuture = this.executor.scheduleAtFixedRate(new CacheExpireDetectTask(this), interval, interval, TimeUnit.SECONDS);
    }

    public boolean cacheExisted(String memberId) {
        if (cachedMemberMap.containsKey(memberId)) {
            CachedMember cacheMember = this.cachedMemberMap.get(memberId);
            if (!cacheMember.isExpired()) {
                return true;
            }
        }
        return false;
    }



    public CachedMember getCachedMember(String memberId) {
        return this.cachedMemberMap.get(memberId);
    }

    public void cacheAdd(Member member, MessageClientType clientType) {
        if (this.cachedMemberMap.containsKey(member.getId())) {
            this.cachedMemberMap.remove(member.getId());
        }
        this.cachedMemberMap.put(member.getId(), new CachedMember(member, clientType, EXPIRED_TIME));
    }

    private static class CacheExpireDetectTask implements Runnable{

        private MemberCacheProvider cacheProvider;

        private CacheExpireDetectTask(MemberCacheProvider cacheProvider) {
            this.cacheProvider = cacheProvider;
        }

        @Override
        public void run() {
            for(Map.Entry<String, CachedMember> entry: cacheProvider.getCachedMemberMap().entrySet()){
                if(entry.getValue() instanceof CachedMember){
                    if(entry.getValue().isExpired()){
                        cacheProvider.getCachedMemberMap().remove(entry.getKey());
                    }
                }

            }
        }
    }

    public Map<String, CachedMember> getCachedMemberMap() {
        return cachedMemberMap;
    }

    public void stopDetect(){
        if(this.scheduledFuture!=null){
            this.scheduledFuture.cancel(true);
        }
    }


}
