package cn.w.im.persistent.mongo.basic;

import cn.w.im.domains.member.BasicMember;
import cn.w.im.domains.member.WcnMember;
import cn.w.im.domains.mongo.relation.MongoWcnMember;
import cn.w.im.persistent.WcnMemberDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component("mongoWcnMemberDao")
public class MongoWcnMemberDaoImpl extends BasicDAO<MongoWcnMember, ObjectId> implements WcnMemberDao {

    @Autowired
    protected MongoWcnMemberDaoImpl(@Qualifier("dataStore") Datastore ds) {
        super(ds);
    }

    @Override
    public void save(WcnMember member) {
        MongoWcnMember mongoMember = new MongoWcnMember(member);
        this.save(mongoMember);
    }

    @Override
    public BasicMember get(String memberId) {
        return this.findOne("id", memberId);
    }
}
