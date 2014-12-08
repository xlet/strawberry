package cn.w.im.core.member.relation;

import cn.w.im.core.exception.ServerInnerException;
import cn.w.im.core.member.provider.MemberInfoProvider;
import cn.w.im.core.member.BasicMember;
import cn.w.im.core.member.provider.MemberNotCachedException;
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

    //TODO:jackie custom member contact persistent.

    public DefaultContactProviderImpl(MemberInfoProvider memberProvider) {
        this.memberProvider = memberProvider;
        this.memberFriendGroupMap = new ConcurrentHashMap<String, List<FriendGroup>>();
    }


    @Override
    public Collection<FriendGroup> getFriendGroup(BasicMember owner) throws ServerInnerException {
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
    public BasicMember getContact(String memberId) throws ContactNotExistedException {
        try {
            return this.memberProvider.getFromCache(memberId);
        } catch (MemberNotCachedException ex) {
            throw new ContactNotExistedException(ex);
        }
    }
}
