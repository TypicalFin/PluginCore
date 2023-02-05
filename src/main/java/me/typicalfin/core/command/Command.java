package me.typicalfin.core.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Command implements CommandExecutor, TabCompleter {

    protected final JavaPlugin plugin;
    protected final CommandInfo info;

    private CommandSender executor;

    public Command(JavaPlugin plugin) {
        this.plugin = plugin;
        this.info = getClass().getAnnotation(CommandInfo.class);
    }

    public boolean handleCommand(CommandSender sender, String[] args) {
        return true;
    }

    public List<String> handleTabComplete(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase(info.name()))
            return false;

        if (info.type() == CommandType.CONSOLE && sender instanceof Player) {
            sender.sendMessage(ChatColor.RED + "You can only execute this command in the console.");
            return true;
        }

        if (info.type() == CommandType.PLAYER && !(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You can only execute this command as a player.");
            return true;
        }

        return handleCommand(sender, args);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase(info.name()) && info.tabCompletes())
            return handleTabComplete(sender, args);

        return null;
    }

    protected void printUsage(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "Invalid usage!");
        // TODO: 05/02/2023 replace <command> with the label instead?
        sender.sendMessage(ChatColor.RED + "Usage: " + ChatColor.GRAY + info.usage().replace("<command>", info.name()));
    }


}
