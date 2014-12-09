package org.xlet.strawberry.persistent.mongo;

import com.mongodb.Mongo;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-2 下午2:09.
 * Summary: MongoFactory.
 */
public class MongoFactoryBean extends AbstractFactoryBean<MongoClient> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 是否主从分离（读取从库），默认读写都在主库.
     */
    private boolean readSecondary = false;

    /**
     * 设定写策略（出错时是否抛异常），默认采取SAFE模式（需要抛异常）.
     */
    private WriteConcern writeConcern = WriteConcern.SAFE;

    private String url;

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

    public void setUrl(String url) {
        logger.debug("set url is:" + url);
        this.url = url;
    }

    @Override
    public Class<?> getObjectType() {
        return Mongo.class;
    }

    @Override
    protected MongoClient createInstance() throws Exception {
        logger.debug("the url is :" + this.url);
        MongoClient mongo = new MongoClient(new MongoClientURI(url));

        if (readSecondary) {
            mongo.setReadPreference(ReadPreference.secondaryPreferred());
        }

        mongo.setWriteConcern(writeConcern);

        return mongo;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
    }
}
