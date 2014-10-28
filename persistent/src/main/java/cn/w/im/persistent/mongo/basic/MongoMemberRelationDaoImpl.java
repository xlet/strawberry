package cn.w.im.persistent.mongo.basic;

import cn.w.im.domains.member.BasicMember;
import cn.w.im.domains.mongo.relation.MongoMemberRelation;
import cn.w.im.domains.relation.MemberRelation;
import cn.w.im.persistent.MemberRelationDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * mongo dao for member relation.
 */
@Component("mongoMemberRelationDao")
public class MongoMemberRelationDaoImpl extends BasicDAO<MongoMemberRelation, ObjectId> implements MemberRelationDao {


    @Autowired
    public MongoMemberRelationDaoImpl(@Qualifier("dataStore") Datastore ds) {
        super(ds);
    }

    @Override
    public void save(MemberRelation memberRelation) {
        MongoMemberRelation mongoMemberRelation = memberRelation.createMongoMemberRelation();
        this.save(mongoMemberRelation);
    }

    @Override
    public MemberRelation get(BasicMember owner) {
        MongoMemberRelation mongoMemberRelation = this.findOne("owner", owner.getId());
        return mongoMemberRelation.createMemberRelation();
    }
}
