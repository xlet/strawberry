package cn.w.im.plugins.init;

import cn.w.im.domains.ServerType;
import cn.w.im.plugins.Plugin;
import cn.w.im.plugins.connected.ConnectedPlugin;
import cn.w.im.plugins.connected.ConnectedResponsePlugin;
import cn.w.im.plugins.connected.MessageClientConnectPlugin;
import cn.w.im.plugins.forward.ForwardReadyPlugin;
import cn.w.im.plugins.forward.ForwardRequestPlugin;
import cn.w.im.plugins.serverRegister.MessageServerRegisterResponsePlugin;
import cn.w.im.plugins.persistentMessage.MessagePersistentPlugin;
import cn.w.im.plugins.innerForwardMessage.InnerForwardMessagePlugin;
import cn.w.im.plugins.requestLinkedClients.RequestLinkedClientsPlugin;
import cn.w.im.plugins.requestLinkedClients.ResponseLinkedClientsPlugin;
import cn.w.im.plugins.login.TokenPlugin;

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
        //forward
        plugins.add(new ForwardRequestPlugin(ServerType.MessageServer));
        plugins.add(new ForwardReadyPlugin(ServerType.MessageServer));

        //starting
        plugins.add(new MessageServerRegisterResponsePlugin(ServerType.MessageServer));
        plugins.add(new RequestLinkedClientsPlugin(ServerType.MessageServer));
        plugins.add(new ResponseLinkedClientsPlugin(ServerType.MessageServer));

        //login
        plugins.add(new TokenPlugin(ServerType.MessageServer));

        //connect
        plugins.add(new ConnectedPlugin(ServerType.MessageServer));
        plugins.add(new ConnectedResponsePlugin(ServerType.MessageServer));
        plugins.add(new MessageClientConnectPlugin(ServerType.MessageServer));

        //inner forward
        plugins.add(new InnerForwardMessagePlugin(ServerType.MessageServer));

        //persistent
        plugins.add(new MessagePersistentPlugin(ServerType.MessageServer));

        return plugins;
    }
}
