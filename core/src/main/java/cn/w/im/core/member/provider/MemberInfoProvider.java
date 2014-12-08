package cn.w.im.core.member.provider;

import cn.w.im.core.member.BasicMember;
import cn.w.im.core.member.relation.FriendGroup;
import cn.w.im.core.ProductType;

import java.util.Collection;

/**
 * member info provider.
 */
public interface MemberInfoProvider {

    /**
     * verify member.check id and password correct.
     *
     * @param loginId     login id.
     * @param password    password.
     * @param productType product type.
     * @return login member.
     * @throws IdPasswordException id and password error exception.
     */
    BasicMember verify(String loginId, String password, ProductType productType) throws IdPasswordException;


    /**
     * get member from cache.
     *
     * @param memberId member id.
     * @return member.
     */
    BasicMember getFromCache(String memberId) throws MemberNotCachedException;

    /**
     * add member.
     *
     * @param basicMember member.
     */
    void addMember(BasicMember basicMember);

    /**
     * get outer system defined system friend group.
     *
     * @param owner owner member.
     * @return friend groups.
     */
    Collection<FriendGroup> getSystemGroup(BasicMember owner);
}
