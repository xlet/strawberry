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
     * member of connected message server map.
     * key memberId+|+clientHost.
     */
    private Map<String, ServerBasic> connectedMemberMap;

    /**
     * key MessageServer nodeId.
     */
    private ConcurrentHashMap<String, MessageServerAllocation> messageServerAllocations;

    /**
     * constructor.
     */
    public DefaultMessageServerAllocateProvider() {
        this.messageServerAllocations = new ConcurrentHashMap<String, MessageServerAllocation>();
        this.connectedMemberMap = new ConcurrentHashMap<String, ServerBasic>();
        //ToDo: jackie config this provider.
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
     * @param clientHost client host.
     * @return ConnectToken.
     */
    public ConnectToken allocate(BasicMember member, String clientHost, MessageClientType clientType) throws LoggedInException {
        LOGGER.debug("allocating message core for client[" + member.getId() + ":" + clientHost + "]");

        LOGGER.debug("check is login in other place");
        if (this.isConnected(member.getId(), clientHost)) {
            //todo:jackie this is wrong,has check  member and clientType.
            //todo:jackie one member is allow login in only one clientType.
            throw new LoggedInException(clientHost);
        }

        int minAllocatedClientCount = Integer.MAX_VALUE;
        MessageServerAllocation matchedMessageServerAllocation = null;
        Iterator<MessageServerAllocation> allocationIterator = this.messageServerAllocations.values().iterator();
        while (allocationIterator.hasNext()) {
            MessageServerAllocation currentMessageServerAllocation = allocationIterator.next();
            ConnectToken connectToken = currentMessageServerAllocation.getLoginToken(member.getId(), clientHost);
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
        LOGGER.debug("allocated message core[" + matchedMessageServerAllocation.getMessageServer().getNodeId() + "] to client.");
        return connectToken;
    }

    @Override
    public void syncAllocation(ConnectToken connectToken) {
        //TODO: jackie sync allocation from other login server,perhaps need modify.
        String nodeId = connectToken.getAllocatedMessageServer().getNodeId();
        MessageServerAllocation messageServerAllocation = this.messageServerAllocations.get(nodeId);
        messageServerAllocation.allocate(connectToken);
    }

    @Override
    public void connected(String connectToken, String memberId, String clientHost, MessageClientType clientType, ServerBasic allocateMessageServer) {
        LOGGER.debug("client[" + memberId + ":" + clientHost + "] connected core[" + allocateMessageServer.getNodeId() + "].");
        String nodeId = allocateMessageServer.getNodeId();
        MessageServerAllocation allocation = this.messageServerAllocations.get(nodeId);
        allocation.connected(connectToken);

        String connectedMemberKey = memberId + "|" + clientHost;
        this.connectedMemberMap.put(connectedMemberKey, allocateMessageServer);
    }

    @Override
    public void disconnected(String memberId, String loginHost, ServerBasic messageServer) {
        LOGGER.debug("client[" + memberId + ":" + loginHost + "] disconnected from core[" + messageServer.getNodeId() + "]");
        String nodeId = messageServer.getNodeId();
        MessageServerAllocation allocation = this.messageServerAllocations.get(nodeId);
        allocation.disconnected(memberId, loginHost);

        if (this.connectedMemberMap.containsKey(memberId)) {
            this.connectedMemberMap.remove(memberId);
        }
    }

    @Override
    public boolean isAllocated(String memberId, String clientHost) {
        Iterator<MessageServerAllocation> allocationIterator = this.messageServerAllocations.values().iterator();
        while (allocationIterator.hasNext()) {
            MessageServerAllocation currentMessageServerAllocation = allocationIterator.next();
            LOGGER.debug("check nodeId=" + currentMessageServerAllocation.getMessageServer().getNodeId());
            ConnectToken connectToken = currentMessageServerAllocation.getLoginToken(memberId, clientHost);
            if (connectToken != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isConnected(String memberId, String clientHost) {
        String connectedmemberkey = memberId + "|" + clientHost;
        if (this.connectedMemberMap.containsKey(connectedmemberkey)) {
            return true;
        }
        return false;
    }
}


