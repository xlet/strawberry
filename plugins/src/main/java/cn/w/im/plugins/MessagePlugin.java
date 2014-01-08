package cn.w.im.plugins;

import cn.w.im.domains.HandlerContext;
import cn.w.im.domains.messages.Message;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-30 下午5:05.
 * Summary: 插件抽象类.
 */
public abstract class MessagePlugin extends AbstractPlugin {

    /**
     * 构造函数.
     * @param name 插件名称.
     * @param description 插件说明.
     */
    public MessagePlugin(String name, String description) {
        super(name, description);
    }

    @Override
    public void process(HandlerContext context) {
        if (isMatch(context)) {
            processMessage(context.getMessage(), context);
        }
    }

    /**
     * 是否匹配.
     * @param context 当前Context.
     * @return 匹配:true  不匹配:false.
     */
    public abstract boolean isMatch(HandlerContext context);

    /**
     * 处理消息.
     * @param message 消息.
     * @param context 当前Context.
     */
    public abstract void processMessage(Message message, HandlerContext context);
}
