package org.xlet.strawberry.core.member.provider;

import org.xlet.strawberry.core.actionSupport.ActionType;
import org.xlet.strawberry.core.actionSupport.MemberInfoChangeLog;
import org.xlet.strawberry.core.exception.ServerInnerException;
import org.xlet.strawberry.core.member.MemberInfoPersistentProvider;
import org.xlet.strawberry.core.member.MemberSourceType;
import org.xlet.strawberry.core.member.BasicMember;
import org.xlet.strawberry.core.member.relation.FriendGroup;
import org.xlet.strawberry.core.ProductType;
import org.xlet.strawberry.core.persistent.PersistentProviderFactory;
import org.xlet.strawberry.core.spring.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * default implement of MemberProvider.
 */
public class DefaultMemberInfoProviderImpl implements MemberInfoProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMemberInfoProviderImpl.class);

    /**
     * member cache.
     * <p/>
     * key:memberId
     */
    private Map<String, BasicMember> memberCacheMap;

    private MemberInfoPersistentProvider persistentProvider;

    public DefaultMemberInfoProviderImpl() throws ServerInnerException {
        this.memberCacheMap = new ConcurrentHashMap<String, BasicMember>();
        this.persistentProvider = PersistentProviderFactory.getPersistentProvider(MemberInfoPersistentProvider.class);
    }

    @Override
    public BasicMember verify(String loginId, String password, ProductType productType) throws IdPasswordException {
        OuterMemberProvider outerMemberProvider = getOuterMemberProvider(productType);
        return outerMemberProvider.verify(loginId, password);
    }

    @Override
    public BasicMember getFromCache(String memberId) throws MemberNotCachedException {
        if (this.memberCacheMap.containsKey(memberId)) {
            return this.memberCacheMap.get(memberId);
        }
        throw new MemberNotCachedException(memberId);
    }

    @Override
    public void addMember(BasicMember basicMember) throws ServerInnerException {
        //add to member info cache.
        if (!this.memberCacheMap.containsKey(basicMember.getId())) {
            this.memberCacheMap.put(basicMember.getId(), basicMember);
        }
        //if member info.
        // not persistent,save.
        //if member info.
        if (this.persistentProvider.exists(basicMember)) {
            if (this.persistentProvider.isChanged(basicMember)) {
                MemberInfoChangeLog memberInfoChangeLog = new MemberInfoChangeLog(basicMember, ActionType.Update);
                memberInfoChangeLog.save();
                this.persistentProvider.update(basicMember);
            }
        } else {
            this.persistentProvider.save(basicMember);
        }
    }

    @Override
    public Collection<FriendGroup> getSystemGroup(BasicMember owner) throws ServerInnerException {
        try {
            if (owner.getMemberSource() == MemberSourceType.OA) {
                OuterMemberProvider outerMemberProvider = this.getOuterMemberProvider(ProductType.OA);
                Collection<FriendGroup> friendGroups = outerMemberProvider.getSystemGroup(owner);
                for (FriendGroup friendGroup : friendGroups) {
                    for (BasicMember member : friendGroup.getContacts()) {
                        if (member != null) {
                            this.addMember(member);
                        }
                    }
                }
                return friendGroups;
            }
        } catch (GetMemberErrorException e) {
            LOGGER.error("get owner[memberId:{}] system friend group error.", owner.getId());
            LOGGER.error(e.getMessage(), e);
        }
        return new ArrayList<FriendGroup>();
    }

    private OuterMemberProvider getOuterMemberProvider(ProductType productType) {
        String beanKey = productType.toString().toLowerCase() + "OuterMemberProvider";
        return SpringContext.context().getBean(beanKey, OuterMemberProvider.class);
    }
}
