package cn.w.im.core.server;

import cn.w.im.core.providers.relation.ContactProvider;
import cn.w.im.core.providers.relation.DefaultContactProviderImpl;
import cn.w.im.core.providers.status.DefaultStatusProvider;
import cn.w.im.core.providers.status.StatusProvider;
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
     * key:token String.
     */
    private Map<String, ConnectToken> tokens;

    private ContactProvider linkmanProvider;

    private StatusProvider statusProvider;

    /**
     * 构造函数.
     *
     * @param port port.
     */
    public MessageServer(int port) {
        super(ServerType.MessageServer, port);
        this.tokens = new ConcurrentHashMap<String, ConnectToken>();
        this.linkmanProvider = new DefaultContactProviderImpl();
        this.statusProvider = new DefaultStatusProvider();
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
     * @param token    message client's token.
     * @param memberId message client's member id.
     * @param host     message client's host.
     * @throws TokenNotExistedException token is not existed.
     * @throws TokenErrorException      token info is different with cached token.
     */
    public void connect(String token, String memberId, String host) throws TokenNotExistedException, TokenErrorException {
        if (!this.tokens.containsKey(token)) {
            throw new TokenNotExistedException(token);
        }
        ConnectToken connectToken = this.tokens.get(token);
        if ((!connectToken.getMember().getId().equals(memberId)) || (!connectToken.getClientHost().equals(host))) {
            throw new TokenErrorException(connectToken.getMember().getId(), connectToken.getClientHost(), memberId, host);
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

    public StatusProvider statusProvider() {
        return this.statusProvider;
    }

    public ContactProvider ContactProvider() {
        return this.linkmanProvider;
    }
}
