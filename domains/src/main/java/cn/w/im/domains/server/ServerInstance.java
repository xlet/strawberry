package cn.w.im.domains.server;

/**
 * Creator: JackieHan.
 * DateTime: 14-2-10 下午3:02.
 * Summary: 服务信息实例.
 */
public class ServerInstance {

    /**
     * 获取服务信息实例.
     *
     * @param serverType 服务类型.
     * @return 服务信息唯一实例.
     * @throws Exception 不支持的服务类型异常.
     */
    public static AbstractServer current(ServerType serverType) throws Exception {
        switch (serverType) {
            case MessageServer:
                return MessageServer.current();
            case MessageBus:
                return MessageBus.current();
            case LoginServer:
                return LoginServer.current();
            default:
                throw new Exception("not supported server type!");
        }
    }
}
