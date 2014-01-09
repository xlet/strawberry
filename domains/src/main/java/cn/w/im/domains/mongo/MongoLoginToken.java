package cn.w.im.domains.mongo;

import cn.w.im.domains.LoginToken;
import org.bson.types.ObjectId;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-8 下午4:09.
 * Summary: 连接消息服务凭证.
 */
public class MongoLoginToken extends LoginToken {

    private ObjectId id;

    /**
     * 构造函数.
     */
    public MongoLoginToken() {

    }

    /**
     * 构造函数.
     * @param token token.
     */
    public MongoLoginToken(LoginToken token) {
        this.setLoginId(token.getLoginId());
        this.setLoginDate(token.getLoginDate());
        this.setUsed(token.isUsed());
        this.setToken(token.getToken());
        this.setClientIp(token.getClientIp());
    }

    /**
     * 获取id.
     * @return id.
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * 设置id.
     * @param id id.
     */
    public void setId(ObjectId id) {
        this.id = id;
    }
}
