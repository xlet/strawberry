package cn.w.im.core.allocate.provider;

import cn.w.im.core.allocate.ConnectToken;
import cn.w.im.core.server.ServerBasic;
import cn.w.im.core.client.MessageClientType;
import cn.w.im.core.member.BasicMember;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-24 下午3:57.
 * Summary: message server allocated provider interface.
 */
public interface MessageServerAllocateProvider {

    /**
     * add ready message server to cache.
     *
     * @param messageServerBasic ready message server basic.
     */
    void messageServerReady(ServerBasic messageServerBasic);

    /**
     * allocate message server to current login message client.
     * <p/>
     * if message client allocated return this allocation.
     *
     * @param member     member id.
     * @param clientType client type.
     * @param clientHost client host.
     * @return ConnectToken.
     */
    ConnectToken allocate(BasicMember member, MessageClientType clientType, String clientHost) throws LoggedInException, ConnectingTokenExistedException;

    /**
     * message client connected message sever.
     *
     * @param connectToken           token string.
     * @param memberId               member id.
     * @param clientType             client type.
     * @param clientHost             client host.
     * @param allocatedMessageServer allocated message server basic.
     */
    void connected(String connectToken, String memberId, MessageClientType clientType, String clientHost, ServerBasic allocatedMessageServer) throws ConnectingTokenNotExistedException, ConnectedStatusExistedException;

    /**
     * message client disconnected message server.
     *
     * @param memberId      member id.
     * @param clientType    client type.
     * @param clientHost    client host.
     * @param messageServer the disconnected message server.
     * @
     */
    void disconnected(String memberId, MessageClientType clientType, String clientHost, ServerBasic messageServer) throws ConnectedStatusNotExistedException;

    /**
     * check if the client was connected
     *
     * @param memberId   member id.
     * @param clientType client type.
     * @return true:connected.
     */
    boolean isConnected(String memberId, MessageClientType clientType);

    /**
     * check if client was allocated.
     *
     * @param memberId   member id.
     * @param clientType client type.
     * @return true:allocated.
     */
    boolean isAllocated(String memberId, MessageClientType clientType);
}
