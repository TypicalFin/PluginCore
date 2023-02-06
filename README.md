# PluginCore
A Bukkit plugin API that makes your life easier.

## Features
- Automatic command registration without plugin.yml entries
- Automatically registers permissions without plugin.yml entries
- Automatic event registration

## Usage
Make your main plugin class `implements CorePlugin` and override the `getCommands()`, `getEvents()` and `getPermissions()` functions to your liking. The `CorePlugin` interface's methods `loadPlugin()`, `enablePlugin()` and `disablePlugin()` should be called when the plugin gets loaded, enabled and disabled. `getCommands()` and `getEvents()` should return an array of all the classes that are commands/event listeners. `getPermissions()` should return an `org.bukkit.permissions.Permission` array.

Commands should have a `@CommandInfo` manifest and `extends me.typicalfin.core.command.Command`.
Event listeners should be regular Bukkit listeners. You can include a constructor that takes a `org.bukkit.plugin.Plugin` as a parameter. Or don't, if you don't need a plugin instance. Up to you.
