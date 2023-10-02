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
import java.util.Objects;

public class GiftCommand implements CommandExecutor, TabCompleter {
    String giftName;
    Player target;
    // /gift get <GiftName> [Player]
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        if (!(commandSender instanceof Player sender)){
            System.out.print("该指令只能在客户端上使用");
            return true;
        }
        switch (args[0]) {
            case "get" -> {
                //检测指令完整性
                if (args.length < 2 || args.length >3){
                    EssentialPluginAPI.message = EssentialPluginAPI.handleMessage("InvalidCommand", sender).replace("{Usage}", Objects.requireNonNull(Bukkit.getPluginCommand("gift")).getUsage());
                    sender.sendMessage(EssentialPluginAPI.message);
                    return true;
                }

                giftName = args[1];
                if (args.length == 3){
                    target = Bukkit.getPlayerExact(args[2]);
                }else{
                    target = sender;
                }
                //检测
                if (!GiftPack.getGiftList().contains(args[1])){
                    EssentialPluginAPI.message = EssentialPluginAPI.handleMessage("Gift.CantFindGift", sender);
                    sender.sendMessage(EssentialPluginAPI.message);
                    return true;
                }

                //实际内容
                if (target == sender){
                    EssentialPluginAPI.message = EssentialPluginAPI.handleMessage("Gift.Get", sender).replace("{GiftName}", giftName);
                }else{
                    EssentialPluginAPI.message = EssentialPluginAPI.handleMessage("Gift.Get2", sender).replace("{GiftName}", giftName).replace("{Target}", target.getName());
                }
                GiftPack.sendGift(giftName, target);
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
            return List.of("get");
        }
        if (args.length == 2){
            return GiftPack.getGiftList();
        }
        if (args.length == 3){
            if (args[0].equals("get")){
                return EssentialPluginAPI.OnlinePlayerNameList();
            }
        }
        return new ArrayList<>();
    }
}
