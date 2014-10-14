package cn.w.im.persistent.mongo.basic;

import cn.w.im.domains.basic.NearlyLinkman;
import cn.w.im.domains.mongo.basic.MongoNearlyLinkman;
import cn.w.im.persistent.NearlyLinkmanDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * mongo implement of {@link cn.w.im.persistent.NearlyLinkmanDao}.
 */
@Component(value = "mongoNearlyLinkmanDao")
public class MongoNearlyLinkmanDaoImpl extends BasicDAO<MongoNearlyLinkman, ObjectId> implements NearlyLinkmanDao {

    @Autowired
    protected MongoNearlyLinkmanDaoImpl(@Qualifier(value = "dataStore") Datastore ds) {
        super(ds);
    }

    @Override
    public NearlyLinkman get(String memberId1, String memberId2) {
        Query<MongoNearlyLinkman> query = this.getDatastore().createQuery(MongoNearlyLinkman.class);
        query.or(
                query.and(query.criteria("memberId1").equal(memberId1), query.criteria("memberId2").equal(memberId2)),
                query.and(query.criteria("memberId1").equal(memberId2), query.criteria("memberId2").equal(memberId1))
        );
        MongoNearlyLinkman mongoNearlyLinkman = this.findOne(query);
        return mongoNearlyLinkman;
    }

    @Override
    public List<NearlyLinkman> get(String memberId, int pageSize, int pageIndex) {
        List<NearlyLinkman> nearlyLinkmen = new ArrayList<NearlyLinkman>();
        Query<MongoNearlyLinkman> query = this.getDatastore().createQuery(MongoNearlyLinkman.class);
        query.or(
                query.criteria("memberId1").equal(memberId),
                query.criteria("memberId2").equal(memberId)
        );
        query.order("-lastLinkTime").offset((pageIndex - 1) * pageSize).limit(pageSize);
        QueryResults<MongoNearlyLinkman> queryResults = this.find(query);
        for (MongoNearlyLinkman mongoNearlyLinkman : queryResults.asList()) {
            nearlyLinkmen.add(mongoNearlyLinkman);
        }
        return nearlyLinkmen;
    }

    @Override
    public void save(NearlyLinkman nearlyLinkman) {
        MongoNearlyLinkman mongoNearlyLinkman = new MongoNearlyLinkman(nearlyLinkman);
        this.save(mongoNearlyLinkman);
    }

    @Override
    public int updateLinkTime(NearlyLinkman nearlyLinkman) {
        Query<MongoNearlyLinkman> query = this.getDatastore().createQuery(MongoNearlyLinkman.class);
        UpdateOperations<MongoNearlyLinkman> updateOperations = this.getDatastore().createUpdateOperations(MongoNearlyLinkman.class).set("lastLinkTime", System.currentTimeMillis());
        UpdateResults updateResults = null;
        if (nearlyLinkman instanceof MongoNearlyLinkman) {
            query.field("_id").equal(((MongoNearlyLinkman) nearlyLinkman).getId());
            updateResults = this.update(query, updateOperations);
        } else {
            String memberId1 = nearlyLinkman.getMemberId1();
            String memberId2 = nearlyLinkman.getMemberId2();
            query.or(
                    query.and(query.criteria("memberId1").equal(memberId1), query.criteria("memberId2").equal(memberId2)),
                    query.and(query.criteria("memberId1").equal(memberId2), query.criteria("memberId2").equal(memberId1))
            );
            updateResults = this.update(query, updateOperations);
        }
        return updateResults.getUpdatedCount();
    }
}
