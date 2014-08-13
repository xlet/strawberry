package cn.w.im.persistent.mongo.basic;

import cn.w.im.domains.basic.OnlineMemberStatus;
import cn.w.im.domains.basic.Status;
import cn.w.im.domains.mongo.basic.MongoOnlineMemberStatus;
import cn.w.im.persistent.OnlineMemberStatusDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * implement {@link cn.w.im.persistent.OnlineMemberStatusDao} with mongo.
 */
@Component("mongoOnlineMemberStatusDao")
public class MongoOnlineMemberStatusDaoImpl extends BasicDAO<MongoOnlineMemberStatus, ObjectId> implements OnlineMemberStatusDao {

    private static final Log LOG = LogFactory.getLog(MongoOnlineMemberStatusDaoImpl.class);

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
        LOG.debug("get member status from mongo db:" + loginId);
        Query<MongoOnlineMemberStatus> query = this.getDatastore().createQuery(MongoOnlineMemberStatus.class);
        query.field("loginId").equal(loginId);
        QueryResults<MongoOnlineMemberStatus> mongoOnlineMemberStatuses = this.find(query);
        MongoOnlineMemberStatus mongoOnlineMemberStatus = this.get(new ObjectId("53eadd81e4b0d7fc7a3785e6"));
        MongoOnlineMemberStatus memberStatus = this.findOne(query);
        if (memberStatus == null) {
            LOG.debug("member status is null,return Offline");
            return new OnlineMemberStatus(loginId, Status.Offline);
        }
        LOG.debug("get member status:" + memberStatus.getStatus());
        return memberStatus;
    }
}
