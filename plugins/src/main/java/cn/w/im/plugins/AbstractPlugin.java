package cn.w.im.plugins;

import cn.w.im.domains.server.ServerType;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-3 下午3:16.
 * Summary: 抽象插件.
 */
public abstract class AbstractPlugin implements Plugin {

    private String name, description;

    private ServerType containerType;

    /**
     * 构造函数.
     *
     * @param name        插件名称.
     * @param description 插件说明.
     * @param containerType 服务类型.
     */
    public AbstractPlugin(String name, String description, ServerType containerType) {
        this.name = name;
        this.description = description;
        this.containerType = containerType;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String description() {
        return this.description;
    }

    @Override
    public ServerType containerType() {
        return containerType;
    }
}
