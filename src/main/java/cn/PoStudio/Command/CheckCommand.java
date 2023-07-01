package cn.PoStudio.Command;

import cn.PoStudio.EP;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CheckCommand implements CommandExecutor {
    public static List<String> checkList = new ArrayList<>();
    public static void HandleCheckList(){
        checkList.add("PlayerLevel");
        checkList.add("PlayerExp");
        checkList.add("PlayerLocalInformation");
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        HandleCheckList();
        String msg;
        Player sender = (Player) commandSender;
        Player target;

        if (command.getName().equals("check")){
            //检测玩家等级
            if (args[0].equals(checkList.get(0))){
                if (args.length <= 2){
                    target = Bukkit.getPlayerExact(args[1]);
                    msg = EP.languageCFG.getString("CheckCommand.Check_PlayerLevel");
                    msg = PlaceholderAPI.setPlaceholders(target, Objects.requireNonNull(msg));
                    sender.sendMessage(msg);
                }
            }
            //检测玩家经验
            if (args[0].equals(checkList.get(1))){
                if (args.length <= 2) {
                    target = Bukkit.getPlayerExact(args[1]);
                    msg = EP.languageCFG.getString("CheckCommand.Check_PlayerExp");
                    msg = PlaceholderAPI.setPlaceholders(target, Objects.requireNonNull(msg));
                    sender.sendMessage(msg);
                }
            }
            //检测玩家位置信息
            if (args[0].equals(checkList.get(2))){
                if (args.length <= 2) {
                    target = Bukkit.getPlayerExact(args[1]);
                    msg = EP.languageCFG.getString("CheckCommand.Check_PlayerLocalInformation");
                    msg = PlaceholderAPI.setPlaceholders(target, Objects.requireNonNull(msg));
                    sender.sendMessage(msg);
                }
            }
        }
        return false;
    }
}
