package cn.w.im.plugins.init;

import cn.w.im.domains.server.ServerType;
import cn.w.im.plugins.Plugin;
import cn.w.im.plugins.forwardMessage.ForwardMessagePlugin;
import cn.w.im.plugins.serverRegister.ServerRegisterPlugin;
import cn.w.im.plugins.persistentMessage.MessagePersistentPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 下午7:14.
 * Summary: 消息总线插件初始化.
 */
public class MessageBusPluginInitializer implements PluginInitializer {
    @Override
    public List<Plugin> init() {
        List<Plugin> plugins = new ArrayList<Plugin>();

        plugins.add(new ServerRegisterPlugin(ServerType.MessageBus));
        plugins.add(new ForwardMessagePlugin(ServerType.MessageBus));
        plugins.add(new MessagePersistentPlugin(ServerType.MessageBus));

        return plugins;
    }
}
