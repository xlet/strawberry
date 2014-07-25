package cn.w.im.persistent.mongo.basic;

import cn.w.im.domains.basic.OnlineMemberStatus;
import cn.w.im.domains.basic.Status;
import cn.w.im.domains.mongo.basic.MongoOnlineMemberStatus;
import cn.w.im.persistent.OnlineMemberStatusDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * implement {@link cn.w.im.persistent.OnlineMemberStatusDao} with mongo.
 */
@Component("mongoOnlineMemberStatusDao")
public class MongoOnlineMemberStatusDaoImpl extends BasicDAO<MongoOnlineMemberStatus, ObjectId> implements OnlineMemberStatusDao {


    @Autowired
    protected MongoOnlineMemberStatusDaoImpl(@Qualifier(value = "dataStore") Datastore ds) {
        super(ds);
    }

    @Override
    public void save(OnlineMemberStatus memberStatus) {
        MongoOnlineMemberStatus mongoOnlineMemberStatus = new MongoOnlineMemberStatus(memberStatus);
        this.save(mongoOnlineMemberStatus);
    }

    @Override
    public void delete(String loginId) {
        Query<MongoOnlineMemberStatus> query = this.getDatastore().createQuery(MongoOnlineMemberStatus.class);
        query.field("loginId").equal(loginId);
        this.deleteByQuery(query);
    }

    @Override
    public OnlineMemberStatus get(String loginId) {
        Query<MongoOnlineMemberStatus> query = this.getDatastore().createQuery(MongoOnlineMemberStatus.class);
        MongoOnlineMemberStatus memberStatus = this.findOne(query);
        if (memberStatus == null) {
            return new OnlineMemberStatus(loginId, Status.Offline);
        }
        return memberStatus;
    }
}
