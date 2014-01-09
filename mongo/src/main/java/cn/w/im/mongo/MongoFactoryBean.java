package cn.w.im.mongo;

import com.mongodb.Mongo;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-2 下午2:09.
 * Summary: MongoFactory.
 */
public class MongoFactoryBean extends AbstractFactoryBean<Mongo> {

    /**
     * mongo连接字符串.
     */
    private String mongoUrl;

    /**
     * 是否主从分离（读取从库），默认读写都在主库.
     */
    private boolean readSecondary = false;

    /**
     * 设定写策略（出错时是否抛异常），默认采取SAFE模式（需要抛异常）.
     */
    private WriteConcern writeConcern = WriteConcern.SAFE;

    /**
     * 设置mongo连接字符串.
     * @param mongoUrl mongo连接字符串.
     */
    public void setMongoUrl(String mongoUrl) {
        this.mongoUrl = mongoUrl;
    }

    /**
     * 设置是否主从分离.
     *
     * @param readSecondary 是否主从分离.
     */
    public void setReadSecondary(boolean readSecondary) {
        this.readSecondary = readSecondary;
    }

    /**
     * 设置写策略.
     *
     * @param writeConcern 写策略.
     */
    public void setWriteConcern(WriteConcern writeConcern) {
        this.writeConcern = writeConcern;
    }

    @Override
    public Class<?> getObjectType() {
        return Mongo.class;
    }

    @Override
    protected Mongo createInstance() throws Exception {
        Mongo mongo = initMongo();

        if (readSecondary) {
            mongo.setReadPreference(ReadPreference.secondaryPreferred());
        }

        mongo.setWriteConcern(writeConcern);

        return mongo;
    }

    /**
     * 初始化Mongo.
     *
     * @return Mongo对象.
     * @throws Exception 异常信息.
     */
    private Mongo initMongo() throws Exception {
        return new MongoClient(new MongoClientURI(mongoUrl));
    }
}
