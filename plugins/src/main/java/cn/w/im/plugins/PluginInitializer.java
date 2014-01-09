package cn.w.im.plugins;

import cn.w.im.plugins.login.LoginPlugin;
import cn.w.im.plugins.persistentMessage.MessageSerizalizePlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-8 下午3:52.
 * Summary: 插件初始化.
 */
public class PluginInitializer {

    /**
     * 初始化.
     * @return 插件列表.
     */
    public static List<Plugin> init() {
        List<Plugin> plugins = new ArrayList<Plugin>();

        plugins.add(new LoginPlugin());
        plugins.add(new TransferMessagePlugin());
        plugins.add(new LogoutPlugin());
        plugins.add(new MessageSerizalizePlugin());

        return plugins;
    }
}
