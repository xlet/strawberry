package cn.w.im.plugins;

import cn.w.im.domains.HandlerContext;

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
     * 处理.
     * @param context hanlder上下文.
     */
    void process(HandlerContext context);
}