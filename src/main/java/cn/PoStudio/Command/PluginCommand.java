package cn.PoStudio.Command;

import cn.PoStudio.EssentialPluginAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PluginCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        switch (args[0]){
            case "reload":
                EssentialPluginAPI.getPlugin().reloadConfig();
                commandSender.sendMessage("§a§lReload Complete");
                break;
            case "version":
                commandSender.sendMessage(EssentialPluginAPI.getPlugin().getDescription().getVersion());
                break;
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        if (args.length == 1){
            return List.of("reload", "version");
        }
        return null;
    }
}
