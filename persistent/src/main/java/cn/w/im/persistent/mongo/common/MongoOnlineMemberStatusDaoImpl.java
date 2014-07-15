package cn.w.im.persistent.mongo.common;

import cn.w.im.domains.common.OnlineMemberStatus;
import cn.w.im.domains.common.Status;
import cn.w.im.domains.mongo.common.MongoOnlineMemberStatus;
import cn.w.im.persistent.OnLineMemberStatusDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * implement {@link cn.w.im.persistent.OnLineMemberStatusDao} with mongo.
 */
@Component("mongoOnlineMemberStatusDao")
public class MongoOnlineMemberStatusDaoImpl extends BasicDAO<MongoOnlineMemberStatus, ObjectId> implements OnLineMemberStatusDao {


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
