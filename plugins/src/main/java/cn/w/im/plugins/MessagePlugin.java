package cn.w.im.plugins;

import cn.w.im.domains.HandlerContext;
import cn.w.im.domains.messages.Message;
import cn.w.im.domains.server.ServerType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-30 下午5:05.
 * Summary: 插件抽象类.
 */
public abstract class MessagePlugin extends AbstractPlugin {

    private Log logger = LogFactory.getLog(this.getClass());

    /**
     * 构造函数.
     *
     * @param name          插件名称.
     * @param description   插件说明.
     * @param containerType 服务类型.
     */
    public MessagePlugin(String name, String description, ServerType containerType) {
        super(name, description, containerType);
    }

    @Override
    public void process(HandlerContext context) {
        try {
            if (isMatch(context)) {
                processMessage(context.getMessage(), context);
            }
        } catch (Exception ex) {
            logger.error(ex);
        }
    }

    /**
     * 是否匹配.
     *
     * @param context 当前Context.
     * @return 匹配:true  不匹配:false.
     */
    public abstract boolean isMatch(HandlerContext context);

    /**
     * 处理消息.
     *
     * @param message 消息.
     * @param context 当前Context.
     */
    public abstract void processMessage(Message message, HandlerContext context);
}
