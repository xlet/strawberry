package cn.w.im.persistent.mongo.dao.status;

import cn.w.im.core.status.recentContact.RecentContactStatusPersistentProvider;
import cn.w.im.core.member.BasicMember;
import cn.w.im.core.member.NotInitMember;
import cn.w.im.persistent.mongo.domain.status.MongoRecentContactStatus;
import cn.w.im.core.status.recentContact.RecentContactStatus;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * mongo recent contact persistent provider implement.
 * <p/>
 * implement of {@link RecentContactStatusPersistentProvider}
 */
@Component("mongoRecentContactStatusPersistentProvider")
public class MongoRecentContactDao extends BasicDAO<MongoRecentContactStatus, ObjectId> implements RecentContactStatusPersistentProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoRecentContactDao.class);

    @Autowired
    protected MongoRecentContactDao(@Qualifier("dataStore") Datastore ds) {
        super(ds);
    }

    @Override
    public void save(Collection<RecentContactStatus> statuses) {
        for (RecentContactStatus status : statuses) {

            String ownerId = status.getOwner().getId();
            String contactId = status.getContact().getId();

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("save recent contact status[{}<->{}]", ownerId, contactId);
            }

            if (!this.exists(status)) {

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("recent contact status[{}<->{}] not exists,create!", ownerId, contactId);
                }

                MongoRecentContactStatus mongoStatus = this.create(status);
                this.save(mongoStatus);
            } else {

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("recent contact status[{}<->{}] exists,update!", ownerId, contactId);
                }
                this.update(status);
            }
        }
    }

    @Override
    public Collection<RecentContactStatus> get(BasicMember owner, int limit) {
        Query<MongoRecentContactStatus> query = this.createQuery();
        query.or(
                query.criteria("memberId1").equal(owner.getId()),
                query.criteria("memberId2").equal(owner.getId())
        );
        query.order("-lastContactTime").limit(limit);
        Collection<MongoRecentContactStatus> mongoStatuses = this.find(query).asList();
        Collection<RecentContactStatus> statuses = new ArrayList<RecentContactStatus>();
        for (MongoRecentContactStatus mongoStatus : mongoStatuses) {
            RecentContactStatus status = this.create(mongoStatus, owner);
            statuses.add(status);
        }
        return statuses;
    }

    private RecentContactStatus create(MongoRecentContactStatus mongoStatus, BasicMember owner) {
        String memberId1 = mongoStatus.getMemberId1();
        String memberId2 = mongoStatus.getMemberId2();
        RecentContactStatus status = new RecentContactStatus();
        status.setOwner(owner);
        if (memberId1.equals(owner.getId())) {
            status.setContact(new NotInitMember(memberId2));
        } else {
            status.setContact(new NotInitMember(memberId1));
        }
        status.setLastMessageContent(mongoStatus.getLastMessageContent());
        status.setLastContactTime(mongoStatus.getLastContactTime());
        return status;
    }


    private void update(RecentContactStatus status) {
        Query<MongoRecentContactStatus> query = this.createMemberQuery(status.getOwner().getId(), status.getContact().getId());
        UpdateOperations<MongoRecentContactStatus> ops = this.createUpdateOperations()
                .set("lastContactTime", status.getLastContactTime())
                .set("lastMessageContent", status.getLastMessageContent());
        this.update(query, ops);
    }

    private Query<MongoRecentContactStatus> createMemberQuery(String memberId1, String memberId2) {
        Query<MongoRecentContactStatus> query = this.createQuery();
        query.or(
                query.and(
                        query.criteria("memberId1").equal(memberId1),
                        query.criteria("memberId2").equal(memberId2)
                ),
                query.and(
                        query.criteria("memberId1").equal(memberId2),
                        query.criteria("memberId2").equal(memberId1)
                )
        );
        return query;
    }

    private boolean exists(RecentContactStatus status) {
        Query<MongoRecentContactStatus> query = this.createMemberQuery(status.getOwner().getId(), status.getContact().getId());
        return this.count(query) != 0;
    }

    private MongoRecentContactStatus create(RecentContactStatus status) {
        MongoRecentContactStatus mongoStatus = new MongoRecentContactStatus();
        mongoStatus.setLastContactTime(status.getLastContactTime());
        mongoStatus.setMemberId1(status.getOwner().getId());
        mongoStatus.setLastMessageContent(status.getLastMessageContent());
        mongoStatus.setMemberId2(status.getContact().getId());
        return mongoStatus;
    }
}
