package cn.w.im.core.providers.relation;

import cn.w.im.core.providers.member.DefaultMemberInfoProviderImpl;
import cn.w.im.core.providers.member.MemberInfoProvider;
import cn.w.im.core.providers.status.DefaultRecentContactProviderImpl;
import cn.w.im.core.providers.status.DefaultStatusProvider;
import cn.w.im.core.providers.status.RecentContactProvider;
import cn.w.im.core.providers.status.StatusProvider;
import cn.w.im.core.member.BasicMember;
import cn.w.im.core.message.client.ProductType;
import cn.w.im.core.member.relation.FriendGroup;
import cn.w.im.core.member.relation.RecentContactStatuses;
import cn.w.im.core.member.MemberStatus;
import cn.w.im.core.exception.ContactNotExistedException;
import cn.w.im.core.exception.MemberNotCachedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * default relation provider.
 */
public class DefaultContactProviderImpl implements ContactProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultContactProviderImpl.class);


    /**
     * member friend group cached map.
     * key:memberId.
     */
    private Map<String, List<FriendGroup>> memberFriendGroupMap;

    private MemberInfoProvider memberProvider;
    private RecentContactProvider recentContactProvider;
    private StatusProvider statusProvider;

    //TODO:jackie custom member contact persistent.

    public DefaultContactProviderImpl() {
        this.memberProvider = new DefaultMemberInfoProviderImpl();
        this.memberFriendGroupMap = new ConcurrentHashMap<String, List<FriendGroup>>();
        this.recentContactProvider = new DefaultRecentContactProviderImpl();
        this.statusProvider = new DefaultStatusProvider();
    }


    @Override
    public Collection<FriendGroup> getFriendGroup(BasicMember owner) {
        // try get from cache.
        if (this.memberFriendGroupMap.containsKey(owner.getId())) {
            return this.memberFriendGroupMap.get(owner.getId());
        }
        // get system group.
        Collection<FriendGroup> friendGroups = new ArrayList<FriendGroup>();
        Collection<FriendGroup> systemGroups = this.memberProvider.getSystemGroup(owner);
        if (!systemGroups.isEmpty()) {
            friendGroups.addAll(systemGroups);
        }

        //get custom group.
        //TODO:jackie custom group
        // add  to cache.
        if (!this.memberFriendGroupMap.containsKey(owner.getId())) {
            List<FriendGroup> cachedMemberFriendGroup = new CopyOnWriteArrayList<FriendGroup>(friendGroups);
            this.memberFriendGroupMap.put(owner.getId(), cachedMemberFriendGroup);
        }
        return friendGroups;
    }


    @Override
    public RecentContactStatuses getRecentContact(BasicMember owner) {
        return this.recentContactProvider.get(owner);
    }

    @Override
    public BasicMember getContact(String memberId, ProductType productType) {
        return null;
    }

    @Override
    public BasicMember getContact(String memberId) throws ContactNotExistedException {
        try {
            return this.memberProvider.getFromCache(memberId);
        } catch (MemberNotCachedException ex) {
            throw new ContactNotExistedException(ex);
        }
    }

    @Override
    public List<BasicMember> getMembers(List<String> ids) {
        return null;
    }

    @Override
    public Collection<MemberStatus> getContactStatus(BasicMember owner) {
        Collection<MemberStatus> memberStatuses = new ArrayList<MemberStatus>();
        Collection<FriendGroup> friendGroups = this.getFriendGroup(owner);
        for (FriendGroup friendGroup : friendGroups) {
            for (BasicMember member : friendGroup.getContacts()) {
                MemberStatus memberStatus = this.statusProvider.status(member);
                memberStatuses.add(memberStatus);
            }
        }
        return memberStatuses;
    }
}
