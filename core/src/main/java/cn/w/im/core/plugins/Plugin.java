package cn.w.im.core.plugins;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-30 下午3:56.
 * Summary: 插件接口.
 */
public interface Plugin {

    /**
     * 获取插件命名.
     *
     * @return 插件名称.
     */
    String name();

    /**
     * 获取插件说明.
     *
     * @return 插件说明.
     */
    String description();

    /**
     * 处理.
     *
     * @param context plugin context.
     */
    void process(PluginContext context);

    /**
     * whether this plugin can process this type of message.
     * @param context plugin context.
     * @return boolean can process.
     */
    boolean isMatch(PluginContext context);
}
