package cn.w.im.core.member.provider;

import cn.w.im.core.member.BasicMember;
import cn.w.im.core.member.relation.FriendGroup;

import java.util.Collection;

/**
 * outer system member provider.
 */
public interface OuterMemberProvider {
    /**
     * verify login id and password,return this member information.
     *
     * @param loginId  member id.
     * @param password password.
     * @return if success return this member information.
     * @throws IdPasswordException member id and password is error.
     */
    BasicMember verify(String loginId, String password) throws IdPasswordException;

    /**
     * get member information.
     *
     * @param memberId member id.
     * @return member information.
     * @throws MemberNotExistedException member not existed.
     * @throws GetMemberErrorException   invoke api error.
     */
    BasicMember get(String memberId) throws MemberNotExistedException, GetMemberErrorException;

    /**
     * get outer system defined system group.
     *
     * @param owner owner member.
     * @return friend groups.
     */
    Collection<FriendGroup> getSystemGroup(BasicMember owner) throws GetMemberErrorException;
}
