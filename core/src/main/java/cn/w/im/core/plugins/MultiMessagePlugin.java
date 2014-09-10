package cn.w.im.core.plugins;

import cn.w.im.domains.ServerType;
import cn.w.im.domains.messages.Message;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportMessageTypeException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * this implement plugin may process multi type message.
 */
public abstract class MultiMessagePlugin extends AbstractPlugin {
    private Log logger = LogFactory.getLog(this.getClass());

    /**
     * 构造函数.
     *
     * @param name        插件名称.
     * @param description 插件说明.
     */
    public MultiMessagePlugin(String name, String description) {
        super(name, description);
    }

    @Override
    public void process(PluginContext context) {
        try {
            processMessage(context.getMessage(), context);
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
     * @throws ClientNotFoundException server not found client.
     */
    protected abstract void processMessage(Message message, PluginContext context) throws ClientNotFoundException;
}
