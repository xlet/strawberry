package cn.w.im.plugins;

import cn.w.im.domains.PluginContext;
import cn.w.im.domains.messages.Message;
import cn.w.im.domains.server.ServerType;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
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
     * @param containerType 服务类型.
     */
    public MessagePlugin(String name, String description, ServerType containerType) {
        super(name, description, containerType);
    }

    @Override
    public void process(PluginContext context) {
        try {
            if (isMatch(context)) {
                logger.debug("matched.");
                @SuppressWarnings("unchecked")
                T message = (T) context.getMessage();
                processMessage(message, context);
            }
        } catch (ClassCastException castException) {
            logger.error("isMatch perhaps error.", castException);
        } catch (NotSupportedServerTypeException notSupportException) {
            logger.error("this plugin[" + this.name() + "] not support this server.", notSupportException);
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
    protected abstract boolean isMatch(PluginContext context);

    /**
     * process message.
     * @param message message.
     * @param context current context.
     * @throws ClientNotFoundException server not found client.
     * @throws NotSupportedServerTypeException this plugin not support server type.
     */
    protected abstract void processMessage(T message, PluginContext context) throws ClientNotFoundException, NotSupportedServerTypeException;
}
