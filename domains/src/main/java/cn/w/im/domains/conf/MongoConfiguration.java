package cn.w.im.domains.conf;

import java.util.Properties;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-4 下午2:06.
 * Summary: mongo配置信息.
 */
public class MongoConfiguration {

    private String url;

    /**
     * 构造函数.
     *
     * @param configProperties 配置信息.
     */
    protected MongoConfiguration(Properties configProperties) {
        this.url = configProperties.getProperty("mongo.url");
    }

    /**
     * 获取Mongo连接字符串.
     *
     * @return mongo连接字符串.
     */
    public String getUrl() {
        return this.url;
    }
}
