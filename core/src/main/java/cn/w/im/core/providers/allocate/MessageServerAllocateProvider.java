package cn.w.im.core.providers.allocate;

import cn.w.im.core.ConnectToken;
import cn.w.im.core.server.ServerBasic;
import cn.w.im.core.MessageClientType;
import cn.w.im.core.member.BasicMember;
import cn.w.im.core.exception.LoggedInException;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-24 下午3:57.
 * Summary: message server allocated provider defined.
 */
public interface MessageServerAllocateProvider {

    //TODO MessageClientType

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
     * @param clientHost client host.
     * @param clientType client type.
     * @return ConnectToken.
     */
    ConnectToken allocate(BasicMember member, String clientHost, MessageClientType clientType) throws LoggedInException;

    /**
     * sync other login server allocation.
     *
     * @param connectToken ConnectToken.
     */
    void syncAllocation(ConnectToken connectToken);

    /**
     * message client connected message sever.
     *
     * @param connectToken           token string.
     * @param memberId               member id.
     * @param clientHost             client host.
     * @param clientType             client type.
     * @param allocatedMessageServer allocated message server basic.
     */
    void connected(String connectToken, String memberId, String clientHost, MessageClientType clientType, ServerBasic allocatedMessageServer);

    /**
     * message client disconnected message server.
     *
     * @param memberId      member id.
     * @param clientHost    client host.
     * @param messageServer the disconnected message server.
     */
    void disconnected(String memberId, String clientHost, ServerBasic messageServer);

    /**
     * check if the client was connected
     *
     * @param memberId   member id.
     * @param clientHost client host.
     * @return true:connected.
     */
    boolean isConnected(String memberId, String clientHost);

    /**
     * check if client was allocated.
     *
     * @param memberId   member id.
     * @param clientHost client host.
     * @return true:allocated.
     */
    boolean isAllocated(String memberId, String clientHost);
}
