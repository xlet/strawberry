package org.xlet.strawberry.core.allocate;

import org.xlet.strawberry.core.allocate.provider.*;
import org.xlet.strawberry.core.client.MessageClientType;
import org.xlet.strawberry.core.server.ServerBasic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-21 下午3:57.
 * Summary: message server allocate info.
 * <p/>
 * record message server who register in login server has allocated message client status and has connected message client status.
 */
public class MessageServerAllocation {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServerAllocation.class);

    private ServerBasic messageServer;

    private int linkedClientCount = 0;

    private int allocateCount = 0;

    private Map<String, Map<MessageClientType, MemberConnectedStatus>> memberConnectedStatusMap;

    /**
     * token.
     */
    private Map<String, ConnectToken> tokenConnectingTokenMap;

    /**
     * key memberId
     */
    private Map<String, Map<MessageClientType, ConnectToken>> loginIdConnectingTokenMap;


    /**
     * constructor.
     *
     * @param messageServer message server basic.
     */
    public MessageServerAllocation(ServerBasic messageServer) {
        this.messageServer = messageServer;
        this.tokenConnectingTokenMap = new ConcurrentHashMap<String, ConnectToken>();
        this.loginIdConnectingTokenMap = new ConcurrentHashMap<String, Map<MessageClientType, ConnectToken>>();
        this.memberConnectedStatusMap = new ConcurrentHashMap<String, Map<MessageClientType, MemberConnectedStatus>>();
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
    public synchronized void allocate(ConnectToken token) throws ConnectingTokenExistedException {
        this.allocateCount += 1;
        this.addConnectingToken(token);
    }

    /**
     * message client connected message server.
     * remove cached login token.
     *
     * @param token      connected token.
     * @param clientHost client host.
     */
    public synchronized void connected(String token, String memberId, MessageClientType clientType, String clientHost) throws ConnectingTokenNotExistedException, ConnectedStatusExistedException {

        if (this.tokenConnectingTokenMap.containsKey(token)) {
            ConnectToken connectToken = this.tokenConnectingTokenMap.get(token);
            this.linkedClientCount += 1;
            this.removeConnectingCache(connectToken);
            this.addConnectedStatus(memberId, clientType, clientHost);
        } else {
            throw new ConnectingTokenNotExistedException(token, memberId, clientType);
        }

    }

    private void addConnectedStatus(String memberId, MessageClientType clientType, String clientHost) throws ConnectedStatusExistedException {
        if (this.memberConnectedStatusMap.containsKey(memberId)) {
            Map<MessageClientType, MemberConnectedStatus> connectedStatuses = this.memberConnectedStatusMap.get(memberId);
            if (connectedStatuses.containsKey(clientType)) {
                throw new ConnectedStatusExistedException(memberId, clientType);
            } else {
                MemberConnectedStatus connectedStatus = new MemberConnectedStatus(memberId, clientType, clientHost);
                connectedStatuses.put(clientType, connectedStatus);
            }
        } else {
            MemberConnectedStatus connectedStatus = new MemberConnectedStatus(memberId, clientType, clientHost);
            Map<MessageClientType, MemberConnectedStatus> clientTypeStatusMap = new ConcurrentHashMap<MessageClientType, MemberConnectedStatus>();
            clientTypeStatusMap.put(clientType, connectedStatus);
            this.memberConnectedStatusMap.put(memberId, clientTypeStatusMap);
        }
    }

    private void removeConnectedStatus(String memberId, MessageClientType clientType, String clientHost) throws ConnectedStatusNotExistedException {
        if (this.memberConnectedStatusMap.containsKey(memberId)) {
            Map<MessageClientType, MemberConnectedStatus> connectedStatusMap = this.memberConnectedStatusMap.get(memberId);
            if (connectedStatusMap.containsKey(clientType)) {
                connectedStatusMap.remove(clientType);
            } else {
                throw new ConnectedStatusNotExistedException(memberId, clientType, clientHost);
            }
        } else {
            throw new ConnectedStatusNotExistedException(memberId, clientType, clientHost);
        }
    }

    private void addConnectingToken(ConnectToken connectToken) throws ConnectingTokenExistedException {
        String token = connectToken.getToken();
        String memberId = connectToken.getMember().getId();
        MessageClientType clientType = connectToken.getClientType();

        if (!this.tokenConnectingTokenMap.containsKey(token)) {
            this.tokenConnectingTokenMap.put(token, connectToken);
        } else {
            throw new ConnectingTokenExistedException(token, memberId, clientType);
        }

        if (this.loginIdConnectingTokenMap.containsKey(memberId)) {
            Map<MessageClientType, ConnectToken> clientTypeConnectingMap = this.loginIdConnectingTokenMap.get(memberId);
            if (clientTypeConnectingMap.containsKey(clientType)) {
                throw new ConnectingTokenExistedException(token, memberId, clientType);
            } else {
                clientTypeConnectingMap.put(clientType, connectToken);
            }
        } else {
            Map<MessageClientType, ConnectToken> clientTypeConnectTokenMap = new ConcurrentHashMap<MessageClientType, ConnectToken>();
            clientTypeConnectTokenMap.put(clientType, connectToken);
            this.loginIdConnectingTokenMap.put(memberId, clientTypeConnectTokenMap);
        }
    }

    private void removeConnectingCache(ConnectToken connectToken) throws ConnectingTokenNotExistedException {
        String token = connectToken.getToken();
        String memberId = connectToken.getMember().getId();
        MessageClientType clientType = connectToken.getClientType();

        if (this.tokenConnectingTokenMap.containsKey(token)) {
            this.tokenConnectingTokenMap.remove(connectToken.getToken());
        } else {
            throw new ConnectingTokenNotExistedException(token, memberId, clientType);
        }

        if (this.loginIdConnectingTokenMap.containsKey(memberId)) {
            Map<MessageClientType, ConnectToken> clientTypeConnectingMap = this.loginIdConnectingTokenMap.get(memberId);
            if (clientTypeConnectingMap.containsKey(clientType)) {
                clientTypeConnectingMap.remove(clientType);
                if (clientTypeConnectingMap.isEmpty()) {
                    this.loginIdConnectingTokenMap.remove(memberId);
                }
            } else {
                throw new ConnectingTokenNotExistedException(token, memberId, clientType);
            }
        } else {
            throw new ConnectingTokenNotExistedException(token, memberId, clientType);
        }
    }

    /**
     * message client disconnected message server.
     *
     * @param memberId   member id.
     * @param clientHost client host.
     */
    public synchronized void disconnected(String memberId, MessageClientType clientType, String clientHost) throws ConnectedStatusNotExistedException {
        this.removeConnectedStatus(memberId, clientType, clientHost);
        this.linkedClientCount -= 1;
    }

    /**
     * get ConnectToken again when message client not received ConnectToken.
     * <p/>
     * if not matched return null.
     *
     * @param memberId   login id.
     * @param clientType client type.
     * @param clientHost message client host.
     * @return created ConnectToken.
     */
    public ConnectToken getLoginToken(String memberId, MessageClientType clientType, String clientHost) throws LoggedInException {
        if (this.loginIdConnectingTokenMap.containsKey(memberId)) {
            Map<MessageClientType, ConnectToken> clientTypeConnectTokenMap = this.loginIdConnectingTokenMap.get(memberId);
            if (clientTypeConnectTokenMap.containsKey(clientType)) {
                ConnectToken token = clientTypeConnectTokenMap.get(clientType);
                if (token.getClientHost().equals(clientHost)) {
                    return token;
                } else {
                    throw new LoggedInException(token.getClientHost());
                }
            }
        }

        return null;
    }

    public boolean isConnected(String memberId, MessageClientType clientType) {
        if (this.memberConnectedStatusMap.containsKey(memberId)) {
            Map<MessageClientType, MemberConnectedStatus> clientTypeStatusMap = this.memberConnectedStatusMap.get(memberId);
            if (clientTypeStatusMap.containsKey(clientType)) {
                //todo:jackie has check client host?
                return true;
            }
        }
        return false;
    }

    public MemberConnectedStatus getConnectedStatus(String memberId, MessageClientType clientType) {
        if (this.memberConnectedStatusMap.containsKey(memberId)) {
            Map<MessageClientType, MemberConnectedStatus> clientTypeStatusMap = this.memberConnectedStatusMap.get(memberId);
            if (clientTypeStatusMap.containsKey(clientType)) {
                return clientTypeStatusMap.get(clientType);
            }
        }
        return null;
    }

    public boolean isAllocated(String memberId, MessageClientType clientType) {
        if (this.loginIdConnectingTokenMap.containsKey(memberId)) {
            Map<MessageClientType, ConnectToken> clientTypeConnectTokenMap = this.loginIdConnectingTokenMap.get(memberId);
            if (clientTypeConnectTokenMap.containsKey(clientType)) {
                return true;
            }
        }
        return false;
    }
}
