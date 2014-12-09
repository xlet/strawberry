package org.xlet.strawberry.persistent.mongo.dao.member;

import org.springframework.stereotype.Component;
import org.xlet.strawberry.core.member.BasicMember;
import org.xlet.strawberry.core.member.MemberInfoPersistentProvider;

/**
 * mongo member dao.
 */
@Component("mongoMemberInfoPersistentProvider")
public class MongoMemberInfoDao implements MemberInfoPersistentProvider {
    @Override
    public void save(BasicMember member) {
        //todo:jackie implement.
    }

    @Override
    public BasicMember get(String memberId) {
        return null;
    }

    @Override
    public boolean isChanged(BasicMember member) {
        return false;
    }

    @Override
    public boolean exists(BasicMember basicMember) {
        return false;
    }

    @Override
    public void update(BasicMember basicMember) {

    }
}
