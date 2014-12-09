package org.xlet.strawberry.persistent.mongo.domain.member;

import org.xlet.strawberry.core.actionSupport.ActionType;
import org.xlet.strawberry.persistent.mongo.domain.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * friend group change history mongo defined.
 */
@Entity("friendGroupChangeHistory")
public class MongoFriendGroupChangeHistory implements MongoDomain {

    @Id
    private ObjectId persistentId;
    private long persistentTime;

    private String friendGroupId;
    private ActionType headerAction;
    private String contactId;
    private ActionType contactActon;

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

    public String getFriendGroupId() {
        return friendGroupId;
    }

    public void setFriendGroupId(String friendGroupId) {
        this.friendGroupId = friendGroupId;
    }

    public ActionType getHeaderAction() {
        return headerAction;
    }

    public void setHeaderAction(ActionType headerAction) {
        this.headerAction = headerAction;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public ActionType getContactActon() {
        return contactActon;
    }

    public void setContactActon(ActionType contactActon) {
        this.contactActon = contactActon;
    }
}
