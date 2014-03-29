package cn.w.im.server;


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

    private boolean init = false;


    private MessageServerAllocateProvider messageServerAllocateProvider;

    /**
     * 构造函数.
     */
    private LoginServer() {
        super(ServerType.LoginServer);
        this.messageServerAllocateProvider = new DefaultMessageServerAllocateProvider();
    }

    /**
     * 获取服务是否初始化.
     *
     * @return true:已经初始化
     */
    public boolean isInit() {
        return init;
    }

    /**
     * 初始化.
     *
     * @param host    绑定Ip.
     * @param port    监听端口.
     * @return 登陆服务信息实例.
     */
    public LoginServer init(String host, int port) {
        if (!init) {
            this.setPort(port);
            this.setHost(host);
            this.init = true;
        }
        return this;
    }

    /**
     * get message server allocate provider.
     * @return allocate provider.
     */
    public MessageServerAllocateProvider allocateProvider() {
        return messageServerAllocateProvider;
    }
}
