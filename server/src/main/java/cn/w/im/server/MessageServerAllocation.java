package cn.w.im.server;

import cn.w.im.domains.ConnectToken;
import cn.w.im.domains.ServerBasic;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-21 下午3:57.
 * Summary: message server allocate info.
 */
public class MessageServerAllocation {

    private ServerBasic messageServer;

    private int linkedClientCount = 0;

    private int allocateCount = 0;

    /**
     * token.
     */
    private ConcurrentHashMap<String, ConnectToken> tokenConnectingTokenMap;

    /**
     * key loginId
     */
    private ConcurrentHashMap<String, List<ConnectToken>> loginIdConnectingTokenMap;

    /**
     * constructor.
     *
     * @param messageServer message server basic.
     */
    public MessageServerAllocation(ServerBasic messageServer) {
        this.messageServer = messageServer;
        this.tokenConnectingTokenMap = new ConcurrentHashMap<String, ConnectToken>();
        this.loginIdConnectingTokenMap = new ConcurrentHashMap<String, List<ConnectToken>>();
    }

    /**
     * get message server basic.
     *
     * @return message server basic.
     */
    public ServerBasic getMessageServer() {
        return this.messageServer;
    }

    /**
     * get linked client count.
     *
     * @return linked client count.
     */
    public int getLinkedClientCount() {
        return this.linkedClientCount;
    }

    /**
     * get allocate count.
     *
     * @return allocate count.
     */
    public int getAllocateCount() {
        return this.allocateCount;
    }

    /**
     * get connecting count.
     *
     * @return connecting count.
     */
    public int getConnectingCount() {
        return this.allocateCount - this.linkedClientCount;
    }

    /**
     * allocate to this message server.
     *
     * @param token login token.
     */
    public synchronized void allocate(ConnectToken token) {
        this.allocateCount += 1;
        if (!tokenConnectingTokenMap.containsKey(token.getToken())) {
            this.tokenConnectingTokenMap.put(token.getToken(), token);
        }
        if (!loginIdConnectingTokenMap.containsKey(token.getClientHost())) {
            List<ConnectToken> loginIdTokens = new CopyOnWriteArrayList<ConnectToken>();
            loginIdTokens.add(token);
            loginIdConnectingTokenMap.put(token.getLoginId(), loginIdTokens);
        } else {
            List<ConnectToken> loginIdTokens = this.loginIdConnectingTokenMap.get(token.getLoginId());
            loginIdTokens.add(token);
        }
    }

    /**
     * message client connected message server.
     * remove cached login token.
     *
     * @param token connected token.
     */
    public synchronized void connected(String token) {
        this.linkedClientCount += 1;
        ConnectToken connectToken = this.tokenConnectingTokenMap.get(token);
        this.tokenConnectingTokenMap.remove(token);

        List<ConnectToken> loginIdTokens = this.loginIdConnectingTokenMap.get(connectToken.getLoginId());
        loginIdTokens.remove(connectToken);

    }

    /**
     * message client disconnected message server.
     *
     * @param loginId    login id.
     * @param clientHost client host.
     */
    public synchronized void disconnected(String loginId, String clientHost) {
        this.linkedClientCount -= 1;
    }

    /**
     * get ConnectToken again when message client not received ConnectToken.
     * <p/>
     * if not matched return null.
     *
     * @param loginId login id.
     * @param host    message client host.
     * @return created ConnectToken.
     */
    public ConnectToken getLoginToken(String loginId, String host) {
        List<ConnectToken> loginIdTokens = this.loginIdConnectingTokenMap.get(loginId);
        if (loginIdTokens == null) {
            return null;
        }
        Iterator<ConnectToken> loginIdTokenIterator = loginIdTokens.iterator();
        while (loginIdTokenIterator.hasNext()) {
            ConnectToken connectToken = loginIdTokenIterator.next();
            if (connectToken.getClientHost().equals(host)) {
                return connectToken;
            }
        }
        return null;
    }
}
