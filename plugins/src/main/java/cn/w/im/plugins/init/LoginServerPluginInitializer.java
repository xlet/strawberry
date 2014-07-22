package cn.w.im.plugins.init;

import cn.w.im.domains.ServerType;
import cn.w.im.plugins.Plugin;
import cn.w.im.plugins.connected.ConnectedPlugin;
import cn.w.im.plugins.forward.ForwardReadyPlugin;
import cn.w.im.plugins.forward.ForwardRequestPlugin;
import cn.w.im.plugins.login.LoginPlugin;
import cn.w.im.plugins.login.TokenPlugin;
import cn.w.im.plugins.login.TokenResponsePlugin;
import cn.w.im.plugins.serverRegister.LoginServerRegisterResponsePlugin;
import cn.w.im.plugins.messageServerReady.MessageServerReadyPlugin;
import cn.w.im.plugins.persistentMessage.MessagePersistentPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 下午5:36.
 * Summary: 登陆服务插件.
 */
public class LoginServerPluginInitializer implements PluginInitializer {

    @Override
    public List<Plugin> init() {
        List<Plugin> plugins = new ArrayList<Plugin>();

        //forward
        plugins.add(new ForwardRequestPlugin(ServerType.LoginServer));
        plugins.add(new ForwardReadyPlugin(ServerType.LoginServer));

        //starting
        plugins.add(new MessageServerReadyPlugin(ServerType.LoginServer));
        plugins.add(new LoginServerRegisterResponsePlugin(ServerType.LoginServer));

        //login
        plugins.add(new LoginPlugin(ServerType.LoginServer));
        plugins.add(new TokenPlugin(ServerType.LoginServer));
        plugins.add(new TokenResponsePlugin(ServerType.LoginServer));

        //connect
        plugins.add(new ConnectedPlugin(ServerType.LoginServer));

        //persistent plugins
        plugins.add(new MessagePersistentPlugin(ServerType.LoginServer));

        //logout


        return plugins;
    }
}
