package cn.w.im.server;

import cn.w.im.domains.*;
import cn.w.im.exceptions.TokenErrorException;
import cn.w.im.exceptions.TokenNotExistedException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Creator: JackieHan.
 * DateTime: 13-11-15 下午1:59.
 * Summary:消息服务器信息.
 */
public class MessageServer extends AbstractServer {

    /**
     * 单例消息服务信息.
     */
    private static MessageServer currentServer;

    /**
     * 单例获取消息服务信息.
     *
     * @return 当前消息服务信息.
     */
    public synchronized static MessageServer current() {
        if (currentServer == null) {
            currentServer = new MessageServer();
        }
        return currentServer;
    }

    /**
     * key:token String.
     */
    private Map<String, ConnectToken> tokens;

    /**
     * 构造函数.
     */
    private MessageServer() {
        super(ServerType.MessageServer);
        this.tokens = new ConcurrentHashMap<String, ConnectToken>();
    }

    /**
     * 添加登陆token.
     *
     * @param token token信息.
     */
    public void addToken(ConnectToken token) {
        this.tokens.put(token.getToken(), token);
    }

    /**
     * get token.
     *
     * @param token token string.
     * @return Connected Token.
     */
    public ConnectToken getToken(String token) {
        return this.tokens.get(token);
    }

    /**
     * connect.
     *
     * @param token   message client's token.
     * @param loginId message client's login id.
     * @param host    message client's host.
     * @throws TokenNotExistedException token is not existed.
     * @throws TokenErrorException      token info is different with cached token.
     */
    public void connect(String token, String loginId, String host) throws TokenNotExistedException, TokenErrorException {
        if (!this.tokens.containsKey(token)) {
            throw new TokenNotExistedException(token);
        }
        ConnectToken connectToken = this.tokens.get(token);
        if ((!connectToken.getLoginId().equals(loginId)) || (!connectToken.getClientHost().equals(host))) {
            throw new TokenErrorException();
        }
    }

    /**
     * connected. remove cached token.
     *
     * @param token token string.
     */
    public void connected(String token) {
        if (this.tokens.containsKey(token)) {
            this.tokens.remove(token);
        }
    }
}
