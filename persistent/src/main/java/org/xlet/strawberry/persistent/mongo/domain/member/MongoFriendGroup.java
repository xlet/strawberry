package org.xlet.strawberry.persistent.mongo.domain.member;

import org.xlet.strawberry.persistent.mongo.domain.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.List;

/**
 * mongo friend group.
 */
@Entity("friendGroup")
public class MongoFriendGroup implements MongoDomain {

    @Id
    private ObjectId persistentId;
    private long persistentTime;

    private String id;
    private String version;
    private List<String> contacts;

    @Override
    public ObjectId getPersistentId() {
        return this.persistentId;
    }

    @Override
    public void setPersistentId(ObjectId id) {
        this.persistentId = id;
    }

    @Override
    public long getPersistentTime() {
        return this.persistentTime;
    }

    @Override
    public void setPersistentTime(long persistentTime) {
        this.persistentTime = persistentTime;
    }
}
