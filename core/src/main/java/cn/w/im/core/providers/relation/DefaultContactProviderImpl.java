package cn.w.im.core.providers.relation;

import cn.w.im.core.providers.member.DefaultMemberProviderImpl;
import cn.w.im.core.providers.member.MemberProvider;
import cn.w.im.domains.MemberSourceType;
import cn.w.im.domains.member.BasicMember;
import cn.w.im.domains.member.OAMember;
import cn.w.im.domains.messages.client.ProductType;
import cn.w.im.domains.relation.FriendGroup;
import cn.w.im.domains.relation.RecentContacts;
import cn.w.im.exceptions.GetMemberErrorException;
import org.apache.commons.lang3.StringUtils;
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

    private static final int RECENT_CONTACT_LIMIT = 50;

    /**
     * system group cached map.
     * <p/>
     * key:orgCode.
     */
    private Map<String, List<FriendGroup>> systemGroupMap;

    /**
     * member friend group cached map.
     * key:memberId.
     */
    private Map<String, List<FriendGroup>> memberFriendGroupMap;

    /**
     * member recent contacts cached map.
     */
    private Map<String, RecentContacts> memberRecentContactsMap;

    private MemberProvider memberProvider;

    public DefaultContactProviderImpl() {
        this.memberProvider = new DefaultMemberProviderImpl();
        this.systemGroupMap = new ConcurrentHashMap<String, List<FriendGroup>>();
        this.memberFriendGroupMap = new ConcurrentHashMap<String, List<FriendGroup>>();
        this.memberRecentContactsMap = new ConcurrentHashMap<String, RecentContacts>();
    }


    @Override
    public Collection<FriendGroup> getFriendGroup(BasicMember owner) {
        // try get from cache.
        if (this.memberFriendGroupMap.containsKey(owner.getId())) {
            return this.memberFriendGroupMap.get(owner.getId());
        }
        // get system group.
        Collection<FriendGroup> friendGroups = new ArrayList<FriendGroup>();
        if (owner.getMemberSource() == MemberSourceType.OA) {
            OAMember oaMember = (OAMember) owner;
            Collection<FriendGroup> oaSystemGroups = getOaSystemGroups(oaMember);
            friendGroups.addAll(oaSystemGroups);
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

    private Collection<FriendGroup> getOaSystemGroups(OAMember owner) {
        if (this.systemGroupMap.containsKey(owner.getOrgCode())) {
            return this.systemGroupMap.get(owner.getOrgCode());
        }
        Map<String, FriendGroup> friendGroupMap = new HashMap<String, FriendGroup>();
        try {
            List<BasicMember> relativeMembers = this.memberProvider.searchById(owner.getOrgCode(), ProductType.OA);
            for (BasicMember member : relativeMembers) {
                OAMember oaMember = (OAMember) member;
                if (member != null && !StringUtils.isEmpty(((OAMember) member).getDepartment())) {
                    if (!friendGroupMap.containsKey(oaMember.getDepartment())) {
                        FriendGroup friendGroup = new FriendGroup("", oaMember.getDepartment(), owner, true);
                        friendGroup.addContract(oaMember);
                        friendGroupMap.put(oaMember.getDepartment(), friendGroup);
                    } else {
                        FriendGroup friendGroup = friendGroupMap.get(oaMember.getDepartment());
                        friendGroup.addContract(oaMember);
                    }
                }
            }
        } catch (GetMemberErrorException e) {
            LOGGER.error("get oa system group error!", e);
            return new ArrayList<FriendGroup>();
        }
        if (!this.systemGroupMap.containsKey(owner.getOrgCode())) {
            List<FriendGroup> cachedFriendGroup = new CopyOnWriteArrayList<FriendGroup>(friendGroupMap.values());
            this.systemGroupMap.put(owner.getOrgCode(), cachedFriendGroup);
        }
        return friendGroupMap.values();
    }


    @Override
    public RecentContacts getRecentContact(BasicMember owner) {
        return null;
    }

    @Override
    public BasicMember getMember(String memberId, ProductType productType) {
        return null;
    }

    @Override
    public List<BasicMember> getMembers(List<String> ids) {
        return null;
    }
}
