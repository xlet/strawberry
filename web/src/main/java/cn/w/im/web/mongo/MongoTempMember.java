package cn.w.im.web.mongo;

import cn.w.im.domains.mongo.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * @author jackie.
 *         temp member.
 */
@Entity("tempMember")
public class MongoTempMember implements MongoDomain {

    @Id
    private ObjectId id;
    private long persistentDate;
    private String name;
    private String nickname;
    private String source;

    @Override
    public ObjectId getId() {
        return this.id;
    }

    @Override
    public void setId(ObjectId id) {
        this.id = id;
    }

    @Override
    public long getPersistentDate() {
        return this.persistentDate;
    }

    @Override
    public void setPersistentDate(long persistentDate) {
        this.persistentDate = persistentDate;
    }

    /**
     * get name.
     *
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * set name.
     *
     * @param name name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get nickname.
     *
     * @return nickname.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * set nickname.
     *
     * @param nickname nickname.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * get source.
     *
     * @return source.
     */
    public String getSource() {
        return source;
    }

    /**
     * set source.
     *
     * @param source source.
     */
    public void setSource(String source) {
        this.source = source;
    }
}
