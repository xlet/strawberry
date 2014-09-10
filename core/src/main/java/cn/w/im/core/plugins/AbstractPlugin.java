package cn.w.im.core.plugins;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-3 下午3:16.
 * Summary: 抽象插件.
 */
public abstract class AbstractPlugin implements Plugin {

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

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String description() {
        return this.description;
    }
}
