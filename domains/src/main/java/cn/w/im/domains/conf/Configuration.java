package cn.w.im.domains.conf;

import java.util.Properties;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-4 下午2:09.
 * Summary: 配置信息.
 */
public class Configuration {

    private static Configuration configuration;

    /**
     * 获取配置信息的单例.
     *
     * @return 配置信息.
     */
    public synchronized static Configuration current() {
        if (configuration == null) {
            configuration = new Configuration();
        }
        return configuration;
    }

    private Configuration() {
    }

    private boolean debug = false;

    private String dataStoreType = "mongo";

    private MongoConfiguration mongoConfiguration;

    private boolean isInit = false;

    /**
     * 初始化配置信息.
     *
     * @param configProperties 配置信息.
     */
    public void init(Properties configProperties) {
        if (!isInit) {
            this.mongoConfiguration = new MongoConfiguration(configProperties);
            if (configProperties.containsKey("debug")) {
                this.debug = Boolean.parseBoolean(configProperties.getProperty("debug"));
            }
            if (configProperties.containsKey("dataStoreType")) {
                this.dataStoreType = configProperties.getProperty("dataStoreType");
            }
            isInit = true;
        }
    }

    /**
     * 获取是否已debug模式运行.
     *
     * @return true:debug.
     */
    public boolean isDebug() {
        return debug;
    }

    /**
     * get data store type.
     *
     * @return data store type.
     */
    public String getDataStoreType() {
        return this.dataStoreType;
    }

    /**
     * 获取mongo配置信息.
     *
     * @return mongo配置信息.
     */
    public MongoConfiguration getMongoConfiguration() {
        return mongoConfiguration;
    }
}
