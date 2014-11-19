package cn.w.im.core.status.recentContact;

import cn.w.im.core.member.BasicMember;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * cached recent contract status collection.
 */
public class CacheRecentContactStatusCollection {

    private Map<String, CacheRecentContactStatus> recentContactStatusMap;

    private Map<String, Collection<CacheRecentContactStatus>> ownerStatusMap;

    public CacheRecentContactStatusCollection() {
        this.recentContactStatusMap = new ConcurrentHashMap<String, CacheRecentContactStatus>();
        this.ownerStatusMap = new ConcurrentHashMap<String, Collection<CacheRecentContactStatus>>();
    }

    public void initOwnerCachedStatus(Collection<RecentContactStatus> statuses) {
        for (RecentContactStatus status : statuses) {
            this.statusChanged(status.getOwner(), status.getContact(), status.getLastContactTime(), status.getLastMessageContent());
        }
    }

    public void statusChanged(BasicMember owner, BasicMember contact, long lastContactTime, String lastMessageContent) {

        if (this.isCached(owner, contact)) {
            CacheRecentContactStatus existedCachedStatus = this.getCachedStatus(owner, contact);
            existedCachedStatus.setLastContactTime(lastContactTime);
            existedCachedStatus.setLastMessageContent(lastMessageContent);
        } else {
            this.add(owner, contact, lastContactTime, lastMessageContent);
        }
    }

    public Collection<RecentContactStatus> getRecentContacts(BasicMember owner) {
        Collection<RecentContactStatus> ownerRecentContactStatus = new ArrayList<RecentContactStatus>();
        if (this.ownerStatusMap.containsKey(owner.getId())) {
            Collection<CacheRecentContactStatus> cachedOwnerRecentContactStatus = this.ownerStatusMap.get(owner.getId());
            for (CacheRecentContactStatus cachedStatus : cachedOwnerRecentContactStatus) {
                ownerRecentContactStatus.add(this.createStatus(owner, cachedStatus));
            }
        }
        return ownerRecentContactStatus;
    }

    public void remove(RecentContactStatus status) {
        String key = this.getKey(status.getOwner(), status.getContact());
        if (this.recentContactStatusMap.containsKey(key)) {
            CacheRecentContactStatus cachedStatus = this.recentContactStatusMap.get(key);
            this.removeOwnerMap(status.getOwner(), cachedStatus);
            this.removeOwnerMap(status.getContact(), cachedStatus);
        }
        this.recentContactStatusMap.remove(key);
    }

    public boolean cached(BasicMember owner) {
        return this.ownerStatusMap.containsKey(owner.getId());
    }

    private void removeOwnerMap(BasicMember owner, CacheRecentContactStatus cachedStatus) {
        if (this.ownerStatusMap.containsKey(owner.getId())) {
            this.ownerStatusMap.get(owner.getId()).remove(cachedStatus);
        }
    }


    private RecentContactStatus createStatus(BasicMember owner, CacheRecentContactStatus cachedStatus) {
        BasicMember contact = cachedStatus.getContact(owner);
        return new RecentContactStatus(owner, contact, cachedStatus.getLastMessageContent(), cachedStatus.getLastContactTime());
    }

    private void add(BasicMember owner, BasicMember contact, long lastContactTime, String lastMessageContent) {
        if (!this.isCached(owner, contact)) {
            String cachedKey = this.getKey(owner, contact);
            CacheRecentContactStatus recentContactStatus = new CacheRecentContactStatus(owner, contact, lastContactTime, lastMessageContent);
            this.recentContactStatusMap.put(cachedKey, recentContactStatus);
            this.addToMemberRecentContactMap(owner, recentContactStatus);
            this.addToMemberRecentContactMap(contact, recentContactStatus);
        }
    }

    private void addToMemberRecentContactMap(BasicMember owner, CacheRecentContactStatus recentContactStatus) {
        if (this.ownerStatusMap.containsKey(owner.getId())) {
            Collection<CacheRecentContactStatus> ownerRecentContactStatus = this.ownerStatusMap.get(owner.getId());
            if (!ownerRecentContactStatus.contains(recentContactStatus)) {
                ownerRecentContactStatus.add(recentContactStatus);
            }
        } else {
            Collection<CacheRecentContactStatus> ownerRecentContactStatus = new CopyOnWriteArrayList<CacheRecentContactStatus>();
            ownerRecentContactStatus.add(recentContactStatus);
            this.ownerStatusMap.put(owner.getId(), ownerRecentContactStatus);
        }
    }


    private CacheRecentContactStatus getCachedStatus(BasicMember owner, BasicMember contact) {
        String key = this.getKey(owner, contact);
        return this.recentContactStatusMap.get(key);
    }

    private boolean isCached(BasicMember owner, BasicMember contact) {
        String key = this.getKey(owner, contact);
        return this.recentContactStatusMap.containsKey(key);
    }

    private String getKey(BasicMember owner, BasicMember contact) {
        if (owner.getId().compareTo(contact.getId()) >= 0) {
            return String.format("{}-{}", owner.getId(), contact.getId());
        } else {
            return String.format("{}-{}", contact.getId(), owner.getId());
        }
    }


}
