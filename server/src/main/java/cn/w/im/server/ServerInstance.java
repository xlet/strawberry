package cn.w.im.server;

import cn.w.im.domains.ServerType;
import cn.w.im.exceptions.NotSupportedServerTypeException;

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
     * @throws NotSupportedServerTypeException 不支持的服务类型异常.
     */
    public static AbstractServer current(ServerType serverType) throws NotSupportedServerTypeException {
        switch (serverType) {
            case MessageServer:
                return MessageServer.current();
            case MessageBus:
                return MessageBus.current();
            case LoginServer:
                return LoginServer.current();
            default:
                throw new NotSupportedServerTypeException(serverType);
        }
    }
}
