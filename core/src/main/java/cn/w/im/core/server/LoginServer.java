package cn.w.im.core.server;


import cn.w.im.core.providers.allocate.DefaultMessageServerAllocateProvider;
import cn.w.im.core.providers.allocate.MessageServerAllocateProvider;
import cn.w.im.domains.ServerType;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-8 下午3:16.
 * Summary: 登录服务信息.
 */
public class LoginServer extends AbstractServer {

    private MessageServerAllocateProvider messageServerAllocateProvider;

    /**
     * 构造函数.
     *
     * @param host host.
     * @param port port.
     */
    public LoginServer(String host, int port) {
        super(ServerType.LoginServer, host, port);
        this.messageServerAllocateProvider = new DefaultMessageServerAllocateProvider();
    }

    /**
     * get message server allocate provider.
     *
     * @return allocate provider.
     */
    public MessageServerAllocateProvider allocateProvider() {
        return messageServerAllocateProvider;
    }
}
