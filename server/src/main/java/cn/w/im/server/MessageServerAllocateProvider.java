package cn.w.im.server;

import cn.w.im.domains.ConnectToken;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.client.MessageClientBasic;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-24 下午3:57.
 * Summary: message server allocated provider defined.
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
     * @param loginId   login id.
     * @param loginHost login host.
     * @return ConnectToken.
     */
    ConnectToken allocate(String loginId, String loginHost);

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
     * @param messageClientBasic     message client basic.
     * @param allocatedMessageServer allocated message server basic.
     */
    void connected(String connectToken, MessageClientBasic messageClientBasic, ServerBasic allocatedMessageServer);

    /**
     * message client disconnected message server.
     *
     * @param loginId       login id.
     * @param loginHost     login host.
     * @param messageServer the disconnected message server.
     */
    void disconnected(String loginId, String loginHost, ServerBasic messageServer);
}
