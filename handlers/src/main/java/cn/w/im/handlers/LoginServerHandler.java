package cn.w.im.handlers;

import cn.w.im.plugins.Plugin;
import cn.w.im.plugins.PluginInitializer;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-8 下午3:47.
 * Summary:
 */
public class LoginServerHandler extends ChannelInboundHandlerAdapter {

    private List<Plugin> plugins = new ArrayList<Plugin>();

    /**
     * 构造函数.
     */
    public LoginServerHandler() {
        plugins = PluginInitializer.init();
    }
}
