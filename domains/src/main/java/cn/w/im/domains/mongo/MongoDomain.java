package cn.w.im.domains.mongo;

import org.bson.types.ObjectId;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 下午7:35.
 * Summary: Mongo结构定义
 */
public interface MongoDomain {

    /**
     * 获取id.
     *
     * @return id.
     */
    ObjectId getPersistentId();

    /**
     * 设置id.
     *
     * @param persistentId id.
     */
    void setPersistentId(ObjectId persistentId);

    /**
     * 获取持久化时间.
     *
     * @return 持久化时间.
     */
    long getPersistentDate();

    /**
     * 设置持久化时间.
     *
     * @param persistentDate 持久化时间.
     */
    void setPersistentDate(long persistentDate);
}
