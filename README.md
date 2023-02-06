# PluginCore
A Bukkit plugin API that makes your life easier.

## Features
- Automatic command registration without plugin.yml entries
- Automatically registers permissions without plugin.yml entries
- Automatic event registration

## Usage
### Registering the plugin
**1.** Make your main plugin class implement the `CorePlugin` interface.</br>
**2.** Override the `getCommands()`, `getEvents()` and `getPermissions()` and make them return your command/event classes. `getPermissions()` should just return an array of Bukkit permissions.</br>
**3.** Call the `CorePlugin` methods `loadPlugin()`, `enablePlugin()` and `disablePlugin()` when your plugin loads/enables/disables.</br>

### Registering events
**1.** Make sure your event class is a regular Bukkit `Listener`.</br>
**2.** Add a constructor that takes in a `org.bukkit.plugin.Plugin`. **(OPTIONAL, use if you need a plugin instance)**</br>
**3.** Add the event class to the `getEvents()` function of your main plugin class.</br>

### Registering commands
**1.** Make a command class that extends `me.typicalfin.core.command.Command`.</br>
**2.** Add a `@CommandInfo` annotation to the command class.</br>
**2.1.** Add a `name()` value to the annotation.</br>
**2.2.** Add other optional values, such as `description()` and `usage()`.</br>
**2.3.** Set `tabCompletes()` to true if you plan on using custom tab completions.</br>
**2.4.** Set `type()` to which command type you want. (**PLAYER** = only players, **CONSOLE** = only console, **BOTH** = both can execute)</br>
**3.** Override `handleCommand(CommandSender, String[])` and optionally `handleTabComplete(CommandSender, String[])`.</br>
**3.1.** `handleCommand(CommandSender, String[])` should return true when the command execution was successful, false if otherwise.</br>
**4.** Add your command class to `getCommands()` function in your main plugin class.</br>

## TODO
- [ ] Add a custom configuration API
- [ ] Add automatic command/event class registering without having to add them to the array
- [ ] Custom PlayerData API
