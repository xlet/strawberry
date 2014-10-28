package cn.w.im.persistent.mongo.basic;

import cn.w.im.domains.member.OAMember;
import cn.w.im.domains.mongo.relation.MongoOaMember;
import cn.w.im.persistent.OaMemberDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * mongo dao for oa member.
 */
@Component("mongoOaMemberDao")
public class MongoOaMemberDaoImpl extends BasicDAO<MongoOaMember, ObjectId> implements OaMemberDao {

    @Autowired
    protected MongoOaMemberDaoImpl(@Qualifier("dataStore") Datastore ds) {
        super(ds);
    }

    @Override
    public void save(OAMember member) {
        MongoOaMember mongoMember = new MongoOaMember(member);
        this.save(mongoMember);
    }

    @Override
    public OAMember get(String memberId) {
        return this.findOne("id", memberId);
    }
}
