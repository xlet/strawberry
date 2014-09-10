package cn.w.im.core.plugins;

import cn.w.im.domains.messages.Message;
import cn.w.im.exceptions.ClientNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-30 下午5:05.
 * Summary: 插件抽象类.
 */
public abstract class MessagePlugin<T extends Message> extends AbstractPlugin {

    private Log logger = LogFactory.getLog(this.getClass());

    /**
     * 构造函数.
     *
     * @param name          插件名称.
     * @param description   插件说明.
     */
    public MessagePlugin(String name, String description) {
        super(name, description);
    }

    @Override
    public void process(PluginContext context) {
        try {
            @SuppressWarnings("unchecked")
            T message = (T) context.getMessage();
            processMessage(message, context);
        } catch (ClassCastException castException) {
            logger.error("isMatch perhaps error.", castException);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    /**
     * process message.
     *
     * @param message message.
     * @param context current context.
     * @throws ClientNotFoundException         server not found client.
     */
    protected abstract void processMessage(T message, PluginContext context) throws ClientNotFoundException;
}
