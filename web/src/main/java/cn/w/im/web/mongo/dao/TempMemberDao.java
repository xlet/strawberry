package cn.w.im.web.mongo.dao;

import cn.w.im.web.mongo.MongoTempMember;
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
@Component
public class TempMemberDao extends BasicDAO<MongoTempMember,ObjectId> {

    @Autowired
    protected TempMemberDao(@Qualifier("dataStore")Datastore ds) {
        super(ds);
    }

    /**
     * get last temp member.
     * @return last temp member.
     */
    public MongoTempMember getLast() {
        Query query = this.getDatastore().createQuery(MongoTempMember.class).order("name").limit(1);
        return this.findOne(query);
    }

    public MongoTempMember getByName(String name) {
        return this.findOne("name",name);
    }
}
