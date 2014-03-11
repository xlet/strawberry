package cn.w.im.plugins.init;

import cn.w.im.domains.server.ServerType;
import cn.w.im.plugins.Plugin;
import cn.w.im.plugins.login.LoginPlugin;
import cn.w.im.plugins.messageServerRegister.MessageServerRegisterPlugin;
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

        plugins.add(new LoginPlugin(ServerType.LoginServer));
        plugins.add(new MessageServerRegisterPlugin(ServerType.LoginServer));
        plugins.add(new MessagePersistentPlugin(ServerType.LoginServer));

        return plugins;
    }
}
