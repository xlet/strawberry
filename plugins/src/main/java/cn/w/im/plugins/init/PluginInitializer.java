package cn.w.im.plugins.init;

import cn.w.im.plugins.Plugin;

import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-8 下午3:52.
 * Summary: 插件初始化.
 */
public interface PluginInitializer {

    /**
     * 初始化.
     * @return 插件列表.
     */
    List<Plugin> init();
}
