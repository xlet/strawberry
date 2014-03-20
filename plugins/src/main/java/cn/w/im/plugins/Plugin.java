package cn.w.im.plugins;

import cn.w.im.domains.PluginContext;
import cn.w.im.domains.server.ServerType;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-30 下午3:56.
 * Summary: 插件接口.
 */
public interface Plugin {

    /**
     * 获取插件命名.
     * @return 插件名称.
     */
    String name();

    /**
     * 获取插件说明.
     * @return 插件说明.
     */
    String description();

    /**
     * 获取注册到那种服务中.
     * @return 服务类型.
     */
    ServerType containerType();

    /**
     * 处理.
     * @param context hanlder上下文.
     */
    void process(PluginContext context);
}
