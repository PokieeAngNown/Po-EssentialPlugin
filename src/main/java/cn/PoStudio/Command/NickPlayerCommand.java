package cn.PoStudio.Command;

import cn.PoStudio.ChatNickName;
import cn.PoStudio.EssentialPluginAPI;
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

public class NickPlayerCommand implements CommandExecutor, TabCompleter {
    /*
        $/nick create [prefix/suffix] [name] [string]
        $/nick remove [prefix/suffix] [name]
        $/nick give [player] [prefix/suffix] [name]
        /nick show [prefix/suffix] [name]
        /nick clear [prefix/suffix/all] [player]
     */
    String message;
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        if (!(commandSender instanceof Player sender)){
            EssentialPluginAPI.getPlugin().getLogger().warning("Can't use this command in Console");
            return true;
        }
        if (args.length == 0){
            message = EssentialPluginAPI.handleMessage("InvalidCommand", sender);
            sender.sendMessage(message);
            return true;
        }
        switch (args[0]) {
            case "create": {
                if (args.length < 4){
                    message = EssentialPluginAPI.handleMessage("InvalidCommand", sender);
                    sender.sendMessage(message);
                    return true;
                }
                String nickType = args[1];
                String nickName = args[2];
                StringBuilder nickString = new StringBuilder();
                for (int i = 3; i < args.length; i++) {
                    nickString.append(args[i]).append(" ");
                }
                nickString = new StringBuilder(nickString.toString().trim() + "§r");
                if (nickString.toString().contains("&")){
                    nickString = new StringBuilder(nickString.toString().replaceAll("&", "§"));
                }
                switch (nickType) {
                    case "prefix": {
                        if (ChatNickName.isNickStore(ChatNickName.NickType.PREFIX, nickName)){
                            message = EssentialPluginAPI.handleMessage("Nick.NickAlreadyStore", sender);
                        }else{
                            ChatNickName.createNick(ChatNickName.NickType.PREFIX, nickName, nickString.toString());
                            message = EssentialPluginAPI.handleMessage("Nick.CreateComplete", sender)
                                    .replace("{NickName}", nickName)
                                    .replace("{NickString}", nickString.toString());
                        }
                        sender.sendMessage(message);
                    }
                    case "suffix": {
                        if (ChatNickName.isNickStore(ChatNickName.NickType.SUFFIX, nickName)){
                            message = EssentialPluginAPI.handleMessage("Nick.NickAlreadyStore", sender);
                        }else{
                            ChatNickName.createNick(ChatNickName.NickType.SUFFIX, nickName, nickString.toString());
                            message = EssentialPluginAPI.handleMessage("Nick.CreateComplete", sender)
                                    .replace("{NickName}", nickName)
                                    .replace("{NickString}", nickString.toString());
                        }
                        sender.sendMessage(message);
                    }
                }
            }
            case "remove": {
                if (args.length != 3){
                    message = EssentialPluginAPI.handleMessage("InvalidCommand", sender);
                    sender.sendMessage(message);
                    return true;
                }
                String nickType = args[1];
                String nickName = args[2];
                switch (nickType) {
                    case "prefix": {
                        if (ChatNickName.isNickStore(ChatNickName.NickType.PREFIX, nickName)){
                            ChatNickName.removeNick(ChatNickName.NickType.PREFIX, nickName);
                            message = EssentialPluginAPI.handleMessage("Nick.RemoveComplete", sender)
                                    .replace("{NickName}", nickName);
                        }else{
                            message = EssentialPluginAPI.handleMessage("Nick.NickNotFound", sender);
                        }
                        sender.sendMessage(message);
                    }
                    case "suffix": {
                        if (ChatNickName.isNickStore(ChatNickName.NickType.SUFFIX, nickName)){
                            ChatNickName.removeNick(ChatNickName.NickType.SUFFIX, nickName);
                            message = EssentialPluginAPI.handleMessage("Nick.RemoveComplete", sender)
                                    .replace("{NickName}", nickName);
                        }else{
                            message = EssentialPluginAPI.handleMessage("Nick.NickNotFound", sender);
                        }
                        sender.sendMessage(message);
                    }
                }
            }
            case "give": {
                if (args.length != 4){
                    message = EssentialPluginAPI.handleMessage("InvalidCommand", sender);
                    sender.sendMessage(message);
                    return true;
                }
                Player target = Bukkit.getPlayerExact(args[1]);
                String nickType = args[2];
                String nickName = args[3];
                switch (nickType){
                    case "prefix": {
                        if (ChatNickName.isNickStore(ChatNickName.NickType.PREFIX, nickName)){
                            ChatNickName.NickPlayer(target, ChatNickName.NickType.PREFIX, ChatNickName.getNick(ChatNickName.NickType.PREFIX, nickName));
                            message = EssentialPluginAPI.handleMessage("Nick.GivePlayerNick", sender)
                                    .replace("{NickName}", nickName);
                        }else{
                            message = EssentialPluginAPI.handleMessage("Nick.NickNotFound", sender);
                        }
                        sender.sendMessage(message);
                    }
                    case "suffix": {
                        if (ChatNickName.isNickStore(ChatNickName.NickType.SUFFIX, nickName)){
                            ChatNickName.NickPlayer(target, ChatNickName.NickType.SUFFIX, ChatNickName.getNick(ChatNickName.NickType.SUFFIX, nickName));
                            message = EssentialPluginAPI.handleMessage("Nick.GivePlayerNick", sender)
                                    .replace("{NickName}", nickName);
                        }else{
                            message = EssentialPluginAPI.handleMessage("Nick.NickNotFound", sender);
                        }
                        sender.sendMessage(message);
                    }
                }
            }
            case "show": {
                if (args.length != 3){
                    message = EssentialPluginAPI.handleMessage("InvalidCommand", sender);
                    sender.sendMessage(message);
                    return true;
                }
                String nickType = args[1];
                String nickName = args[2];
                switch (nickType){
                    case "prefix": {
                        if (ChatNickName.isNickStore(ChatNickName.NickType.PREFIX, nickName)){
                            message = EssentialPluginAPI.handleMessage("Nick.GivePlayerNick", sender)
                                    .replace("{NickName}", nickName);
                        }else{
                            message = EssentialPluginAPI.handleMessage("Nick.NickNotFound", sender);
                        }
                    }
                    case "suffix": {

                    }
                }
            }
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        if (args.length == 1){
            return List.of("create", "remove", "give");
        }
        switch (args[0]){
            case "create", "remove":
                if (args.length == 2){
                    return List.of("prefix", "suffix");
                }
                if (args.length == 3){
                    if (!args[0].equals("create")){
                        if (args[1].equals("prefix")){
                            return ChatNickName.getNickList(ChatNickName.NickType.PREFIX);
                        }
                        if (args[1].equals("suffix")){
                            return ChatNickName.getNickList(ChatNickName.NickType.SUFFIX);
                        }
                    }
                }
                break;
            case "give":
                if (args.length == 2){
                    List<Player> playerList = new ArrayList<Player>(Bukkit.getOnlinePlayers());
                    List<String> stringList = new ArrayList<>();
                    for (Player player : playerList) {
                        stringList.add(player.getDisplayName());
                    }
                    return stringList;
                }
                if (args.length == 3){
                    return List.of("prefix", "suffix");
                }
                if (args.length == 4){
                    if (args[2].equals("prefix")){
                        return ChatNickName.getNickList(ChatNickName.NickType.PREFIX);
                    }
                    if (args[2].equals("suffix")){
                        return ChatNickName.getNickList(ChatNickName.NickType.SUFFIX);
                    }
                }
                break;
        }
        return new ArrayList<>();
    }
}
