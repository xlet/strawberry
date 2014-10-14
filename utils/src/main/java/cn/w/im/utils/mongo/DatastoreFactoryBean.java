package cn.w.im.utils.mongo;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-2 下午4:06.
 * Summary:
 */
public class DatastoreFactoryBean extends AbstractFactoryBean<Datastore> {

    /**
     * morphia实例.
     */
    private Morphia morphia;

    /**
     * mongo client实例.
     */
    private MongoClient mongoClient;

    /**
     * 数据库名称.
     */
    private String dbName;

    /**
     * 是否确认索引存在.
     */
    private boolean toEnsureIndexes = false;

    /**
     * 是否确认Caps存在.
     */
    private boolean toEnsureCaps = false;

    /**
     * 设置Morphia实例.
     *
     * @param morphia Morphia实例.
     */
    public void setMorphia(Morphia morphia) {
        this.morphia = morphia;
    }

    /**
     * 设置mongo实例.
     *
     * @param mongoClient mongo实例.
     */
    public void setMongoClient(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    /**
     * 设置库名称.
     *
     * @param dbName 库名称.
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     * 设置是否确认索引是否存在.
     *
     * @param toEnsureIndexes 是否确认索引是否存在.
     */
    public void setToEnsureIndexes(boolean toEnsureIndexes) {
        this.toEnsureIndexes = toEnsureIndexes;
    }

    /**
     * 设置是否确认Caps是否存在.
     *
     * @param toEnsureCaps 是否确认Caps是否存在.
     */
    public void setToEnsureCaps(boolean toEnsureCaps) {
        this.toEnsureCaps = toEnsureCaps;
    }

    @Override
    public Class<?> getObjectType() {
        return Datastore.class;
    }

    @Override
    protected Datastore createInstance() throws Exception {
        Datastore datastore = morphia.createDatastore(mongoClient, dbName);

        if (toEnsureIndexes) {
            datastore.ensureIndexes();
        }

        if (toEnsureCaps) {
            datastore.ensureCaps();
        }

        return datastore;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        if (mongoClient == null) {
            throw new IllegalStateException("mongo is not set");
        }
        if (morphia == null) {
            throw new IllegalArgumentException("morphia is not set");
        }
    }
}
