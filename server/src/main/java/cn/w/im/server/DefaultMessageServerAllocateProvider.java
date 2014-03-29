package cn.w.im.server;

import cn.w.im.domains.ConnectToken;
import cn.w.im.domains.ServerBasic;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-21 下午3:54.
 * Summary: login server allocate message server provider.
 */
public class DefaultMessageServerAllocateProvider implements MessageServerAllocateProvider {

    private TokenProvider tokenProvider;

    /**
     * key MessageServer nodeId.
     */
    private ConcurrentHashMap<String, MessageServerAllocation> messageServerAllocations;

    /**
     * constructor.
     */
    public DefaultMessageServerAllocateProvider() {
        messageServerAllocations = new ConcurrentHashMap<String, MessageServerAllocation>();
        //ToDo: jackie config this provider.
        tokenProvider = new DefaultTokenProvider();
    }

    /**
     * add ready message server to cache.
     *
     * @param messageServerBasic ready message server basic.
     */
    public void messageServerReady(ServerBasic messageServerBasic) {
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
     * @param loginId   login id.
     * @param loginHost login host.
     * @return ConnectToken.
     */
    public ConnectToken allocate(String loginId, String loginHost) {
        int minAllocatedClientCount = Integer.MAX_VALUE;
        MessageServerAllocation matchedMessageServerAllocation = null;
        Iterator<MessageServerAllocation> allocationIterator = this.messageServerAllocations.values().iterator();
        while (allocationIterator.hasNext()) {
            MessageServerAllocation currentMessageServerAllocation = allocationIterator.next();
            ConnectToken connectToken = currentMessageServerAllocation.getLoginToken(loginId, loginHost);
            if (connectToken != null) {
                return connectToken;
            }
            if (minAllocatedClientCount <= currentMessageServerAllocation.getAllocateCount()) {
                matchedMessageServerAllocation = currentMessageServerAllocation;
                minAllocatedClientCount = currentMessageServerAllocation.getAllocateCount();
            }
        }

        ConnectToken connectToken = new ConnectToken(loginHost, loginId, tokenProvider.create(), matchedMessageServerAllocation.getMessageServer());
        matchedMessageServerAllocation.allocate(connectToken);
        return connectToken;
    }

    @Override
    public void syncAllocation(ConnectToken connectToken) {
        String nodeId = connectToken.getAllocatedMessageServer().getNodeId();
        MessageServerAllocation messageServerAllocation = this.messageServerAllocations.get(nodeId);
        messageServerAllocation.allocate(connectToken);
    }

    /**
     * message client connected message sever.
     *
     * @param connectToken ConnectToken.
     */
    public void connected(String connectToken, ServerBasic allocateMessageServer) {
        String nodeId = allocateMessageServer.getNodeId();
        MessageServerAllocation allocation = this.messageServerAllocations.get(nodeId);
        allocation.connected(connectToken);
    }

    /**
     * message client disconnected message server.
     *
     * @param loginId       login id.
     * @param loginHost     login host.
     * @param messageServer the disconnected message server.
     */
    public void disconnected(String loginId, String loginHost, ServerBasic messageServer) {
        String nodeId = messageServer.getNodeId();
        MessageServerAllocation allocation = this.messageServerAllocations.get(nodeId);
        allocation.disconnected(loginId, loginHost);
    }
}


