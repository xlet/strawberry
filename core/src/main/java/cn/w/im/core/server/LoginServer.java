package cn.w.im.core.server;


import cn.w.im.core.allocate.DefaultMessageServerAllocateProvider;
import cn.w.im.core.allocate.MessageServerAllocateProvider;
import cn.w.im.domains.ServerType;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-8 下午3:16.
 * Summary: 登录服务信息.
 */
public class LoginServer extends AbstractServer {

    private static LoginServer currentLoginServer;

    /**
     * 单例获取LoginServer.
     *
     * @return 登陆服务信息.
     */
    public synchronized static LoginServer current() {
        if (currentLoginServer == null) {
            currentLoginServer = new LoginServer();
        }
        return currentLoginServer;
    }

    private MessageServerAllocateProvider messageServerAllocateProvider;

    /**
     * 构造函数.
     */
    private LoginServer() {
        super(ServerType.LoginServer);
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
