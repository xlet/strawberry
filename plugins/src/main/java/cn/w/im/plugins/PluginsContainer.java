package cn.w.im.plugins;

import cn.w.im.core.plugins.Plugin;
import cn.w.im.plugins.connected.ConnectedPlugin;
import cn.w.im.plugins.connected.ConnectedResponsePlugin;
import cn.w.im.plugins.connected.MessageClientConnectPlugin;
import cn.w.im.plugins.forward.ForwardReadyPlugin;
import cn.w.im.plugins.forward.ForwardRequestPlugin;
import cn.w.im.plugins.forwardMessage.ForwardMessagePlugin;
import cn.w.im.plugins.innerForwardMessage.InnerForwardMessagePlugin;
import cn.w.im.plugins.login.LoginPlugin;
import cn.w.im.plugins.login.TokenPlugin;
import cn.w.im.plugins.login.TokenResponsePlugin;
import cn.w.im.plugins.logout.LogoutPlugin;
import cn.w.im.plugins.member.MemberProfilePlugin;
import cn.w.im.plugins.messageServerReady.MessageServerReadyPlugin;
import cn.w.im.plugins.persistentMessage.MessagePersistentPlugin;
import cn.w.im.plugins.requestLinkedClients.RequestLinkedClientsPlugin;
import cn.w.im.plugins.requestLinkedClients.ResponseLinkedClientsPlugin;
import cn.w.im.plugins.serverRegister.LoginServerRegisterResponsePlugin;
import cn.w.im.plugins.serverRegister.MessageServerRegisterResponsePlugin;
import cn.w.im.plugins.serverRegister.ServerRegisterPlugin;
import cn.w.im.plugins.status.StatusPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * plugin container. contain all plugins.
 */
public class PluginsContainer {

    private static final List<Plugin> allPlugins = new ArrayList<Plugin>();

    static {
        allPlugins.add(new ConnectedPlugin());
        allPlugins.add(new ConnectedResponsePlugin());
        allPlugins.add(new MessageClientConnectPlugin());

        allPlugins.add(new ForwardReadyPlugin());
        allPlugins.add(new ForwardRequestPlugin());

        allPlugins.add(new ForwardMessagePlugin());

        allPlugins.add(new InnerForwardMessagePlugin());

        allPlugins.add(new LoginPlugin());
        allPlugins.add(new TokenPlugin());
        allPlugins.add(new TokenResponsePlugin());

        allPlugins.add(new LogoutPlugin());

        allPlugins.add(new MemberProfilePlugin());

        allPlugins.add(new MessageServerReadyPlugin());

        //allPlugins.add(new NearlyLinkmanPlugin());

        allPlugins.add(new MessagePersistentPlugin());

        allPlugins.add(new RequestLinkedClientsPlugin());
        allPlugins.add(new ResponseLinkedClientsPlugin());

        allPlugins.add(new LoginServerRegisterResponsePlugin());
        allPlugins.add(new MessageServerRegisterResponsePlugin());
        allPlugins.add(new ServerRegisterPlugin());

        allPlugins.add(new StatusPlugin());
    }

    public static List<Plugin> all() {
        return allPlugins;
    }
}
