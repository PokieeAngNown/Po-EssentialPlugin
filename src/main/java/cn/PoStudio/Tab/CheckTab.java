package cn.PoStudio.Tab;

import cn.PoStudio.Command.CheckCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CheckTab implements TabCompleter {
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandsender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        CheckCommand.HandleCheckList();
        if (args.length == 1){
            list = CheckCommand.checkList;
            return list;
        }
        if (args.length >= 3){
            return list;
        }
        return null;
    }
}
