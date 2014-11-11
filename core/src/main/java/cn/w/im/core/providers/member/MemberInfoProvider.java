package cn.w.im.core.providers.member;

import cn.w.im.core.member.BasicMember;
import cn.w.im.core.member.relation.FriendGroup;
import cn.w.im.core.message.client.ProductType;
import cn.w.im.core.exception.GetMemberErrorException;
import cn.w.im.core.exception.IdPasswordException;
import cn.w.im.core.exception.MemberNotCachedException;
import cn.w.im.core.exception.MemberNotExistedException;

import java.util.Collection;
import java.util.List;

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
     * get member.
     *
     * @param memberId    member id.
     * @param productType product type.
     * @return member.
     */
    BasicMember get(String memberId, ProductType productType) throws MemberNotExistedException, GetMemberErrorException;


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
