package cn.w.im.persistent.mongo.basic;

import cn.w.im.domains.member.BasicMember;
import cn.w.im.domains.member.NotInitMember;
import cn.w.im.domains.mongo.basic.MongoContactStatus;
import cn.w.im.domains.relation.RecentContactItem;
import cn.w.im.domains.status.ContactStatus;
import cn.w.im.persistent.ContactStatusDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * mongo contact status dao.
 */
@Component("mongoContactStatusDao")
public class MongoContactStatusDaoImpl extends BasicDAO<MongoContactStatus, ObjectId> implements ContactStatusDao {

    @Autowired
    protected MongoContactStatusDaoImpl(@Qualifier("dataStore") Datastore ds) {
        super(ds);
    }

    @Override
    public void save(ContactStatus contactStatus) {
        MongoContactStatus mongoContactStatus = this.get(contactStatus.getMember1().getId(), contactStatus.getMember2().getId());
        if (mongoContactStatus == null) {
            mongoContactStatus.setMemberId1(contactStatus.getMember1().getId());
            mongoContactStatus.setMemberId2(contactStatus.getMember2().getId());
            mongoContactStatus.setLastContactTime(contactStatus.getLastContactTime());
            mongoContactStatus.setLastMessage(contactStatus.getLastMessage());
            this.save(mongoContactStatus);
        } else {
            UpdateOperations<MongoContactStatus> updateOperations = this.createUpdateOperations()
                    .set("lastMessage", contactStatus.getLastMessage())
                    .set("lastContactTime", contactStatus.getLastContactTime())
                    .set("persistentDate", System.currentTimeMillis());
            Query<MongoContactStatus> query = this.createQuery()
                    .filter("memberId1=", mongoContactStatus.getMemberId1())
                    .filter("memberId2", mongoContactStatus.getMemberId2());
            this.update(query, updateOperations);
        }
    }

    @Override
    public List<ContactStatus> getContactStatus(BasicMember member, int limit) {
        Query<MongoContactStatus> query = this.createQuery();
        query.or(
                query.criteria("memberId1").equal(member.getId()),
                query.criteria("memberId2").equal(member.getId())
        );
        QueryResults<MongoContactStatus> queryResults = this.find(query);
        List<MongoContactStatus> mongoContactStatuses = queryResults.asList();

        List<ContactStatus> contactStatuses = new ArrayList<ContactStatus>();
        for (MongoContactStatus mongoContactStatus : mongoContactStatuses) {
            ContactStatus contactStatus = new ContactStatus();
            contactStatus.setLastMessage(mongoContactStatus.getLastMessage());
            contactStatus.setLastContactTime(mongoContactStatus.getLastContactTime());
            contactStatus.setMember1(new NotInitMember(mongoContactStatus.getMemberId1()));
            contactStatus.setMember2(new NotInitMember(mongoContactStatus.getMemberId2()));
            contactStatuses.add(contactStatus);
        }
        return contactStatuses;
    }

    private MongoContactStatus get(String memberId1, String memberId2) {

        Query<MongoContactStatus> query = this.getDatastore().createQuery(MongoContactStatus.class);
        query.or(
                query.and(
                        query.criteria("memberId1").equal(memberId1), query.criteria("memberId2").equal(memberId2)
                ),
                query.and(query.criteria("memberId1").equal(memberId2), query.criteria("memberId2").equal(memberId1))
        );
        return this.findOne(query);
    }
}
