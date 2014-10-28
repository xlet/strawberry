package cn.w.im.persistent;

import cn.w.im.domains.member.BasicMember;
import cn.w.im.domains.relation.MemberRelation;

/**
 * member relation dao.
 */
public interface MemberRelationDao {

    void save(MemberRelation memberRelation);

    MemberRelation get(BasicMember owner);
}
