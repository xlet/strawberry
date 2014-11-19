package cn.w.im.core.member.provider;

import cn.w.im.core.member.MemberSourceType;
import cn.w.im.core.member.BasicMember;
import cn.w.im.core.member.relation.FriendGroup;
import cn.w.im.core.ProductType;
import cn.w.im.core.spring.SpringContext;
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

    public DefaultMemberInfoProviderImpl() {
        this.memberCacheMap = new ConcurrentHashMap<String, BasicMember>();
    }

    @Override
    public BasicMember verify(String loginId, String password, ProductType productType) throws IdPasswordException {
        OuterMemberProvider outerMemberProvider = getOuterMemberProvider(productType);
        return outerMemberProvider.verify(loginId, password);
    }

    @Override
    public BasicMember get(String memberId, ProductType productType) throws MemberNotExistedException, GetMemberErrorException {
        String key = memberId;
        //try get from cache.
        if (this.memberCacheMap.containsKey(key)) {
            return this.memberCacheMap.get(key);
        }

        //try get from outer system. if not null then add to cache.
        OuterMemberProvider outerMemberProvider = this.getOuterMemberProvider(productType);
        BasicMember member = outerMemberProvider.get(memberId);
        if (member != null) {
            this.addMember(member);
        }
        return member;
    }

    @Override
    public BasicMember getFromCache(String memberId) throws MemberNotCachedException {
        if (this.memberCacheMap.containsKey(memberId)) {
            return this.memberCacheMap.get(memberId);
        }
        throw new MemberNotCachedException(memberId);
    }

    @Override
    public void addMember(BasicMember basicMember) {
        if (!this.memberCacheMap.containsKey(basicMember.getId())) {
            this.memberCacheMap.put(basicMember.getId(), basicMember);
        }
        //todo:jackie check member changed.
    }

    @Override
    public Collection<FriendGroup> getSystemGroup(BasicMember owner) {
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
