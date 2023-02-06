package me.typicalfin.core;

import lombok.experimental.UtilityClass;
import me.typicalfin.core.command.Command;
import me.typicalfin.core.command.CommandInfo;
import me.typicalfin.core.util.ReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.util.*;

@UtilityClass
public class PluginCore {

    // TODO: 05/02/2023 auto register events/commands feature
    
    public void loadPlugin(CorePlugin plugin) {
        assert plugin instanceof Plugin;

        final Plugin bukkitPlugin = (Plugin) plugin;

        injectCommands(bukkitPlugin, plugin.getCommands());
        injectPermissions(bukkitPlugin, plugin.getPermissions());
    }

    public void enablePlugin(CorePlugin plugin) {
        assert plugin instanceof JavaPlugin;

        final JavaPlugin bukkitPlugin = (JavaPlugin) plugin;

        registerEvents(bukkitPlugin, plugin.getEvents());
        registerCommands(bukkitPlugin, plugin.getCommands());
    }

    public void disablePlugin(CorePlugin plugin) {
        assert plugin instanceof Plugin;
    }

    private void injectCommands(Plugin plugin, Class<?>[] commands) {
        if (commands.length == 0)
            return;

        final PluginDescriptionFile descriptionFile = plugin.getDescription();
        final Map<String, Map<String, Object>> originalMap = descriptionFile.getCommands();
        Map<String, Map<String, Object>> commandMap = new HashMap<>(originalMap == null ? new HashMap<>() : descriptionFile.getCommands());

        for (Class<?> clazz : commands) {
            final CommandInfo info = clazz.getAnnotation(CommandInfo.class);

            if (info == null) {
                plugin.getLogger().severe("Unable to load command " + clazz.getName() + " due to a missing CommandInfo annotation!");
                continue;
            }

            final String name = info.name();
            final String description = info.description();
            final String[] aliases = info.aliases().split(",");
            final String permission = info.permission();
            final String permissionMessage = info.permissionMessage();
            final String usage = info.usage();

            if (name.isEmpty()) {
                plugin.getLogger().severe("Unable to load command " + clazz.getName() + " due to a missing command name!");
                continue;
            }

            final Map<String, Object> map = new HashMap<>();

            if (!description.isEmpty())
                map.put("description", description);

            if (aliases.length > 0)
                map.put("aliases", Arrays.asList(aliases));

            if (!permission.isEmpty())
                map.put("permission", permission);

            if (!permissionMessage.isEmpty())
                map.put("permission-message", permissionMessage);

            if (!usage.isEmpty())
                map.put("usage", usage);

            commandMap.put(name, map);
        }

        ReflectionUtil.setFieldValue(PluginDescriptionFile.class, "commands", commandMap, descriptionFile);
    }

    private void injectPermissions(Plugin plugin, Permission[] permissions) {
        if (permissions.length == 0)
            return;

        final PluginDescriptionFile descriptionFile = plugin.getDescription();

        final List<Permission> permissionList = new ArrayList<>(descriptionFile.getPermissions());
        permissionList.addAll(Arrays.asList(permissions));

        ReflectionUtil.setFieldValue(PluginDescriptionFile.class, "permissions", permissionList, descriptionFile);
    }

    private void registerEvents(Plugin plugin, Class<?>[] listeners) {
        if (listeners.length == 0)
            return;

        final PluginManager pluginManager = Bukkit.getPluginManager();

        for (Class<?> clazz : listeners) {
            if (!Listener.class.isAssignableFrom(clazz)) continue;

            Listener instance;
            Constructor<?> constructor;

            try {
                constructor = clazz.getDeclaredConstructor(Plugin.class);
                instance = (Listener) constructor.newInstance(plugin);
            } catch (Exception ignored) {
                try {
                    constructor = clazz.getDeclaredConstructor();
                    instance = (Listener) constructor.newInstance();
                } catch (Exception ex) {
                    plugin.getLogger().severe("Unable to register event " + clazz.getName() + "!");
                    ex.printStackTrace();
                    return;
                }
            }

            pluginManager.registerEvents(instance, plugin);
        }
    }

    private void registerCommands(JavaPlugin plugin, Class<?>[] commands) {
        if (commands.length == 0)
            return;

        for (Class<?> clazz : commands) {
            final CommandInfo info = clazz.getAnnotation(CommandInfo.class);

            if (info == null)
                continue;

            final PluginCommand pluginCommand = plugin.getCommand(info.name());

            if (pluginCommand == null)
                continue;

            try {
                final Command command = (Command) clazz.getDeclaredConstructor(JavaPlugin.class).newInstance(plugin);

                pluginCommand.setExecutor(command);
                pluginCommand.setTabCompleter(command);
            } catch (Exception ex) {
                plugin.getLogger().severe("Failed to register command " + info.name() + "!");
                ex.printStackTrace();
            }
        }
    }

}
