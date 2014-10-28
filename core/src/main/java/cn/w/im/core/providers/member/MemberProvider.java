package cn.w.im.core.providers.member;

import cn.w.im.domains.member.BasicMember;
import cn.w.im.domains.messages.client.ProductType;
import cn.w.im.exceptions.GetMemberErrorException;
import cn.w.im.exceptions.IdPasswordException;
import cn.w.im.exceptions.MemberNotExistedException;

import java.util.List;

/**
 *
 */
public interface MemberProvider {

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
     * add member.
     *
     * @param basicMember member.
     */
    void addMember(BasicMember basicMember, ProductType productType);

    /**
     * search by id.
     *
     * @param idString    id string.
     * @param productType product type.
     * @return members.
     */
    List<BasicMember> searchById(String idString, ProductType productType) throws GetMemberErrorException;
}
