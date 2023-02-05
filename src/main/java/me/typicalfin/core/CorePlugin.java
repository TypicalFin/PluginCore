package me.typicalfin.core;

import org.bukkit.permissions.Permission;

public interface CorePlugin {

    default Class<?>[] getCommands() {
        return new Class[0];
    }

    default Class<?>[] getEvents() {
        return new Class[0];
    }

    default Permission[] getPermissions() {
        return new Permission[0];
    }

    default void loadPlugin() {
        PluginCore.loadPlugin(this);
    }

    default void enablePlugin() {
        PluginCore.enablePlugin(this);
    }

    default void disablePlugin() {
        PluginCore.disablePlugin(this);
    }

}
