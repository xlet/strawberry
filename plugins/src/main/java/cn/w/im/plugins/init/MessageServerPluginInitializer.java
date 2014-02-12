package cn.w.im.plugins.init;

import cn.w.im.domains.server.ServerType;
import cn.w.im.plugins.Plugin;
import cn.w.im.plugins.logout.LogoutPlugin;
import cn.w.im.plugins.messageServerRegisterResponse.MessageServerRegisterResponsePlugin;
import cn.w.im.plugins.persistentMessage.MessagePersistentPlugin;
import cn.w.im.plugins.innerForwardMessage.InnerForwardMessagePlugin;
import cn.w.im.plugins.requestLinkedClients.RequestLinkedClientsPlugin;
import cn.w.im.plugins.responseLinkedClients.ResponseLinkedClientsPlugin;
import cn.w.im.plugins.token.TokenPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-15 上午9:36.
 * Summary: 消息服务插件初始化.
 */
public class MessageServerPluginInitializer implements PluginInitializer {
    @Override
    public List<Plugin> init() {
        List<Plugin> plugins = new ArrayList<Plugin>();
        plugins.add(new LogoutPlugin(ServerType.MessageServer));
        plugins.add(new InnerForwardMessagePlugin(ServerType.MessageServer));
        plugins.add(new MessageServerRegisterResponsePlugin(ServerType.MessageServer));
        plugins.add(new TokenPlugin(ServerType.MessageServer));
        plugins.add(new RequestLinkedClientsPlugin(ServerType.MessageServer));
        plugins.add(new ResponseLinkedClientsPlugin(ServerType.MessageServer));
        plugins.add(new MessagePersistentPlugin(ServerType.MessageServer));

        return plugins;
    }
}
