package cn.w.im.plugins.init;

import cn.w.im.domains.server.ServerType;
import cn.w.im.utils.spring.SpringContext;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 下午5:51.
 * Summary: plugin初始化对象工厂.
 */
public class PluginInitializerFactory {

    /**
     * 获取初始化对象.
     * @param serverType 服务类型.
     * @return 特定初始对象.
     */
    public static PluginInitializer getInitializer(ServerType serverType) {

        String serverString = serverType.toString();
        serverString = (new StringBuilder().append(Character.toLowerCase(serverString.charAt(0))).append(serverString.substring(1))).toString();
        String key = serverString + "PluginInitializer";
        return (PluginInitializer) SpringContext.context().getBean(key);
    }
}
