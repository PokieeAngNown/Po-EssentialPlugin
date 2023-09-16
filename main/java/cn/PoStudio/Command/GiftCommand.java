package cn.PoStudio.Command;

import cn.PoStudio.EssentialPluginAPI;
import cn.PoStudio.Function.GiftPack;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GiftCommand implements CommandExecutor, TabCompleter {
    String giftName;
    String playerName;
    // /gift get [GiftName]
    // /gift send [GiftName] [Player]
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        if (!(commandSender instanceof Player sender)){
            System.out.print("该指令只能在客户端上使用");
            return true;
        }
        switch (args[0]) {
            case "get" -> {
                giftName = args[1];
                if (!GiftPack.getGiftList().contains(args[1])){
                    EssentialPluginAPI.message = EssentialPluginAPI.handleMessage("Gift.CantFindGift", sender);
                    sender.sendMessage(EssentialPluginAPI.message);
                    return true;
                }
                GiftPack.sendGift(giftName, sender);
                EssentialPluginAPI.message = EssentialPluginAPI.handleMessage("Gift.GetGift", sender).replace("{GiftName}", args[1]);
                sender.sendMessage(EssentialPluginAPI.message);
                return true;
            }
            case "send" -> {
                Player target = Bukkit.getPlayerExact(args[2]);
                giftName = args[1];
                if (target == null){
                    EssentialPluginAPI.message = EssentialPluginAPI.handleMessage("PlayerNotFound", target);
                    sender.sendMessage(EssentialPluginAPI.message);
                    return true;
                }
                playerName = target.getName();
                if (!GiftPack.getGiftList().contains(args[1])){
                    EssentialPluginAPI.message = EssentialPluginAPI.handleMessage("Gift.CantFindGift", target);
                    sender.sendMessage(EssentialPluginAPI.message);
                    return true;
                }
                GiftPack.sendGift(giftName, target);
                EssentialPluginAPI.message = EssentialPluginAPI.handleMessage("Gift.SendGift", target).replace("{GiftName}", giftName).replace("{GiftTarget}", playerName);
                sender.sendMessage(EssentialPluginAPI.message);
                return true;
            }
            default -> {
                EssentialPluginAPI.message = command.getUsage();
                sender.sendMessage(EssentialPluginAPI.message);
                return true;
            }
        }
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        if (args.length == 1){
            return List.of("get", "send");
        }
        if (args.length == 2){
            return GiftPack.getGiftList();
        }
        if (args.length == 3){
            if (args[0].equals("send")){
                return EssentialPluginAPI.OnlinePlayerNameList();
            }
        }
        return new ArrayList<>();
    }
}
