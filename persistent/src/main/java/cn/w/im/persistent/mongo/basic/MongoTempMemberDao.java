package cn.w.im.persistent.mongo.basic;

import cn.w.im.domains.member.TempMember;
import cn.w.im.domains.mongo.basic.MongoTempMember;
import cn.w.im.persistent.TempMemberDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author jackie.
 */
@Component("mongoTempMemberDao")
public class MongoTempMemberDao extends BasicDAO<MongoTempMember, ObjectId> implements TempMemberDao {

    @Autowired
    protected MongoTempMemberDao(@Qualifier("dataStore") Datastore ds) {
        super(ds);
    }

    @Override
    public void save(TempMember tempMember) {
        MongoTempMember mongoTempMember = new MongoTempMember(tempMember);
    }

    public TempMember getLast() {
        Query query = this.getDatastore().createQuery(MongoTempMember.class).order("name").limit(1);
        return this.findOne(query);
    }

    public TempMember get(String memberId) {
        return this.findOne("id", memberId);
    }
}
