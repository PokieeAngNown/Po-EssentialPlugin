package cn.PoStudio.Command;

import cn.PoStudio.EssentialPluginAPI;
import cn.PoStudio.Function.CustomItemStack;
import cn.PoStudio.Function.CustomItemStack.CItem;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomItemCommand implements CommandExecutor, TabCompleter {
    String fileName; // example.yml
    String itemName;
    Player target;
    // /cItem get <fileName> <itemID> [Player]

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        if (!(commandSender instanceof Player sender)){
            System.out.print("该指令只能在客户端上使用");
            return true;
        }
        switch (args[0]){
            case "get" -> {
                //检测指令完整性
                if (args.length < 3 || args.length >4){
                    EssentialPluginAPI.message = EssentialPluginAPI.handleMessage("InvalidCommand", sender).replace("{Usage}", Objects.requireNonNull(Bukkit.getPluginCommand("cItem")).getUsage());
                    sender.sendMessage(EssentialPluginAPI.message);
                    return true;
                }

                fileName = args[1];
                itemName = args[2];
                if (args.length == 4){
                    target = Bukkit.getPlayerExact(args[3]);
                }else{
                    target = sender;
                }
                //检测指令存在性
                if (!CustomItemStack.getItemFiles().contains(new File(EssentialPluginAPI.getPlugin().getDataFolder() + "/itemStack", fileName))){
                    EssentialPluginAPI.message = EssentialPluginAPI.handleMessage("CustomItem.CantFindFile", sender);
                    sender.sendMessage(EssentialPluginAPI.message);
                    return true;
                }

                if (!CustomItemStack.isItemInFile(fileName, itemName)){
                    EssentialPluginAPI.message = EssentialPluginAPI.handleMessage("CustomItem.CantFindItem", sender).replace("{FileName}", fileName);
                    sender.sendMessage(EssentialPluginAPI.message);
                    return true;
                }


                //实际内容
                if (target == sender){
                    EssentialPluginAPI.message = EssentialPluginAPI.handleMessage("CustomItem.Get", sender).replace("{ItemName}", itemName);
                }else{
                    EssentialPluginAPI.message = EssentialPluginAPI.handleMessage("CustomItem.Get2", sender).replace("{ItemName}", itemName).replace("{Target}", target.getName());
                }
                CItem cItem = CustomItemStack.getItem(fileName, itemName);
                //sender.sendMessage(cItem.getDisplayName());
                CustomItemStack.sendItemToPlayer(cItem, target);
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
            if (CustomItemStack.getItemFiles().isEmpty()){
                return new ArrayList<>();
            }else{
                List<File> list = CustomItemStack.getItemFiles();
                List<String> nameList = new ArrayList<>();
                for (File file : list) {
                    nameList.add(file.getName());
                }
                return nameList;
            }
        }
        if (args.length == 3){
            List<CItem> list = CustomItemStack.getItems(args[1]);
            List<String> nameList = new ArrayList<>();
            for (CItem cItem : list) {
                nameList.add(cItem.getName());
            }
            return nameList;
        }
        if (args.length == 4){
            if (args[0].equals("get")){
                return EssentialPluginAPI.OnlinePlayerNameList();
            }
        }
        return new ArrayList<>();
    }
}
