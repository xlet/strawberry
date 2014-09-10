package cn.w.im.core.plugins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * default plugin provider implement.
 */
public class DefaultPluginProviderImpl implements PluginProvider {

    private List<Plugin> plugins;

    private DefaultPluginProviderImpl() {
        this.plugins = new CopyOnWriteArrayList<Plugin>();
    }

    public DefaultPluginProviderImpl(Collection<Plugin> plugins) {
        this();
        this.plugins.addAll(plugins);
    }

    @Override
    public void add(Plugin plugin) {
        this.plugins.add(plugin);
    }

    @Override
    public void add(Collection<Plugin> plugins) {
        this.plugins.addAll(plugins);
    }

    @Override
    public List<Plugin> getMatchedPlugins(PluginContext context) {
        List<Plugin> matchedPlugins = new ArrayList<Plugin>();
        for (Plugin plugin : plugins) {
            if (plugin.isMatch(context)) {
                matchedPlugins.add(plugin);
            }
        }
        return matchedPlugins;
    }
}
