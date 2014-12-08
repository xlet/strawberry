package cn.w.im.persistent.mongo.domain;

import org.bson.types.ObjectId;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 下午7:35.
 * Summary: Mongo结构定义
 */
public interface MongoDomain {

    ObjectId getPersistentId();

    void setPersistentId(ObjectId id);

    long getPersistentTime();

    void setPersistentTime(long persistentTime);
}
