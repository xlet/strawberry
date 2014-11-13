package cn.w.im.core.providers.allocate;

import cn.w.im.core.ConnectToken;
import cn.w.im.core.server.ServerBasic;
import cn.w.im.core.MessageClientType;
import cn.w.im.core.member.BasicMember;
import cn.w.im.core.exception.LoggedInException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-21 下午3:54.
 * Summary: login server allocate message server provider.
 */
public class DefaultMessageServerAllocateProvider implements MessageServerAllocateProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMessageServerAllocateProvider.class);
    private TokenProvider tokenProvider;

    /**
     * key MessageServer nodeId.
     */
    private Map<String, MessageServerAllocation> messageServerAllocations;

    /**
     * constructor.
     */
    public DefaultMessageServerAllocateProvider() {
        this.messageServerAllocations = new ConcurrentHashMap<String, MessageServerAllocation>();
        tokenProvider = new DefaultTokenProvider();
    }

    /**
     * add ready message server to cache.
     *
     * @param messageServerBasic ready message server basic.
     */
    public void messageServerReady(ServerBasic messageServerBasic) {
        LOGGER.debug("message core[" + messageServerBasic.getNodeId() + "] ready to allocated");
        if (!this.messageServerAllocations.containsKey(messageServerBasic.getNodeId())) {
            messageServerAllocations.put(messageServerBasic.getNodeId(), new MessageServerAllocation(messageServerBasic));
        }
    }

    /**
     * allocate message server to current login message client.
     * average message client to each message server allocate arithmetic.
     * <p/>
     * if message client allocated return this allocation.
     *
     * @param member     login member.
     * @param clientType client type.
     * @param clientHost client host.
     * @return ConnectToken.
     */
    public ConnectToken allocate(BasicMember member, MessageClientType clientType, String clientHost)
            throws LoggedInException, ConnectingTokenExistedException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("allocating message server for client[member:{},clientHost:{},clientType:{}]", member.getId(), clientHost, clientType);
        }

        if (this.isConnected(member.getId(), clientType)) {
            MemberConnectedStatus connectedStatus = this.getConnectedStatus(member, clientType);
            if (connectedStatus != null) {
                throw new LoggedInException(connectedStatus.getConnectedHost());
            }
        }

        int minAllocatedClientCount = Integer.MAX_VALUE;
        MessageServerAllocation matchedMessageServerAllocation = null;
        Iterator<MessageServerAllocation> allocationIterator = this.messageServerAllocations.values().iterator();
        while (allocationIterator.hasNext()) {
            MessageServerAllocation currentMessageServerAllocation = allocationIterator.next();
            ConnectToken connectToken = currentMessageServerAllocation.getLoginToken(member.getId(), clientType, clientHost);
            if (connectToken != null) {
                return connectToken;
            }
            if (currentMessageServerAllocation.getAllocateCount() <= minAllocatedClientCount) {
                matchedMessageServerAllocation = currentMessageServerAllocation;
                minAllocatedClientCount = currentMessageServerAllocation.getAllocateCount();
            }
        }

        ConnectToken connectToken = new ConnectToken(clientType, clientHost, member, tokenProvider.create(),
                matchedMessageServerAllocation.getMessageServer());
        matchedMessageServerAllocation.allocate(connectToken);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("allocated message server[{}] to client.", matchedMessageServerAllocation.getMessageServer().getNodeId());
        }

        return connectToken;
    }

    private MemberConnectedStatus getConnectedStatus(BasicMember member, MessageClientType clientType) {
        Iterator<MessageServerAllocation> allocationIterator = this.messageServerAllocations.values().iterator();
        while (allocationIterator.hasNext()) {
            MessageServerAllocation currentAllocation = allocationIterator.next();
            if (currentAllocation.isConnected(member.getId(), clientType)) {
                return currentAllocation.getConnectedStatus(member.getId(), clientType);
            }
        }
        return null;
    }

    @Override
    public void connected(String connectToken, String memberId, MessageClientType clientType, String clientHost,
                          ServerBasic connectedServer) throws ConnectingTokenNotExistedException, ConnectedStatusExistedException {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("client[member:{},clientType:{},clientHost{}] connected on message server[{}].",
                    memberId, clientType, clientHost, connectedServer.getNodeId());
        }

        String nodeId = connectedServer.getNodeId();
        MessageServerAllocation allocation = this.messageServerAllocations.get(nodeId);
        allocation.connected(connectToken, memberId, clientType, clientHost);
    }

    @Override
    public void disconnected(String memberId, MessageClientType clientType, String clientHost, ServerBasic serverBasic)
            throws ConnectedStatusNotExistedException {
        if (LOGGER.isDebugEnabled()) {

            LOGGER.debug("client[member:{},clientType:{},clientHost{}] disconnected on message server[{}].",
                    memberId, clientType, clientHost, serverBasic.getNodeId());
        }
        String nodeId = serverBasic.getNodeId();
        MessageServerAllocation allocation = this.messageServerAllocations.get(nodeId);
        allocation.disconnected(memberId, clientType, clientHost);
    }

    @Override
    public boolean isAllocated(String memberId, MessageClientType clientType) {
        Iterator<MessageServerAllocation> allocationIterator = this.messageServerAllocations.values().iterator();
        while (allocationIterator.hasNext()) {
            MessageServerAllocation currentAllocation = allocationIterator.next();
            if (currentAllocation.isAllocated(memberId, clientType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isConnected(String memberId, MessageClientType clientType) {
        Iterator<MessageServerAllocation> allocationIterator = this.messageServerAllocations.values().iterator();
        while (allocationIterator.hasNext()) {
            MessageServerAllocation currentAllocation = allocationIterator.next();
            if (currentAllocation.isConnected(memberId, clientType)) {
                return true;
            }
        }
        return false;
    }
}


