package cn.w.im.domains.client;

import cn.w.im.domains.ServerBasic;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 下午3:28.
 * Summary: 登录服务做为客户端.
 */
public class LoginServerClient extends ServerClient {

    /**
     * 构造函数.
     * @param serverBasic 服务基础信息.
     */
    public LoginServerClient(ServerBasic serverBasic) {
        super(serverBasic, ClientType.LoginServer);
    }
}
