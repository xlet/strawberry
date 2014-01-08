package cn.w.im.plugins;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-3 下午3:16.
 * Summary:
 */
public abstract class AbstractPlugin implements Plugin {

    /**
     * name:插件名称.
     * description:插件说明.
     */
    private String name, description;

    /**
     * 构造函数.
     *
     * @param name        插件名称.
     * @param description 插件说明.
     */
    public AbstractPlugin(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * 获取插件名称.
     *
     * @return 插件名称.
     */
    @Override
    public String name() {
        return this.name;
    }

    /**
     * 获取插件说明.
     *
     * @return 插件说明.
     */
    @Override
    public String description() {
        return this.description;
    }
}
