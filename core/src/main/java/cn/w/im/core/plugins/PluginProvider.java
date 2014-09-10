package cn.w.im.core.plugins;

import java.util.Collection;
import java.util.List;

/**
 * plugin provider.
 */
public interface PluginProvider {

    /**
     * add one plugin.
     *
     * @param plugin plugin instance.
     */
    void add(Plugin plugin);

    /**
     * add more plugins.
     *
     * @param plugins collection of plugin instances.
     */
    void add(Collection<Plugin> plugins);

    /**
     * get matched plugins by message type.
     *
     * @param context plugin context.
     * @return matched plugins.
     */
    List<Plugin> getMatchedPlugins(PluginContext context);
}
