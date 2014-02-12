package cn.w.im.domains.client;

import cn.w.im.domains.ServerBasic;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 下午3:27.
 * Summary: 消息服务客户端.
 */
public class MessageServerClient extends ServerClient {

    /**
     * 构造函数.
     * @param serverBasic 服务信息.
     */
    public MessageServerClient(ServerBasic serverBasic) {
        super(serverBasic, ClientType.MessageServer);
    }
}
