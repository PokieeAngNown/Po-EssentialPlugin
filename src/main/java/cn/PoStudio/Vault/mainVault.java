package cn.PoStudio.Vault;

import cn.PoStudio.EP;
import cn.PoStudio.EntityNBT.*;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class mainVault implements CommandExecutor, TabCompleter {
    public static Plugin plugin = Bukkit.getPluginManager().getPlugin("EssentialPlugin");
    public static Integer getVault(Entity player, String VaultType){
        if (handleNBT.hasNBT(player, new NamespacedKey(plugin, "vault." + VaultType))){
            return (Integer) handleNBT.getNBT(player, new NamespacedKey(plugin, "vault." + VaultType));
        }else{
            return -1;
        }
    }

    public static void setVault(Player player, String VaultType, Integer amount){
        handleNBT.setNBT(player, new NamespacedKey(plugin, "vault." + VaultType), amount);
    }

    public static void removeVault(Player player, String VaultType){
        handleNBT.removeNBT(player, new NamespacedKey(plugin, "vault." + VaultType));
    }

    public void defaultVault(Player player){
        if (!handleNBT.hasNBT(player, new NamespacedKey(plugin, "vault.money"))){
            handleNBT.setNBT(player, new NamespacedKey(plugin, "vault.money"), 0);
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        Player target;
        String vaultType;
        if (commandSender instanceof Player){
            Player sender = (Player) commandSender;
            target = Bukkit.getPlayerExact(args[1]);
            vaultType = args[2];

            defaultVault(target);

            if (getVault(target, vaultType) == -1){
                sender.sendMessage(Objects.requireNonNull(EP.languageCFG.getString("Vault.VaultTypeNotFound")));
                return true;
            }

            switch (args[0]){
                case "get":
                    if (args.length != 3){
                        sender.sendMessage(Objects.requireNonNull(EP.languageCFG.getString("InvalidCommand")));
                        return true;
                    }
                    sender.sendMessage(EP.HandlePlaceholderAPI(target, Objects.requireNonNull(EP.languageCFG.getString("Vault.GetPlayerVault"))));
                    break;
                case "set":
                    if (args.length != 4){
                        sender.sendMessage(Objects.requireNonNull(EP.languageCFG.getString("InvalidCommand")));
                        return true;
                    }
                    try{
                        setVault(target, vaultType, Integer.valueOf(args[3]));
                        sender.sendMessage(EP.HandlePlaceholderAPI(target, Objects.requireNonNull(EP.languageCFG.getString("Vault.SetPlayerVault"))));
                    }catch (NumberFormatException numberFormatException){
                        setVault(target,vaultType, 0);
                        sender.sendMessage(Objects.requireNonNull(EP.languageCFG.getString("Vault.OutOfInteger")));
                    }
                    break;
                case "add":
                    if (args.length != 4){
                        sender.sendMessage(Objects.requireNonNull(EP.languageCFG.getString("InvalidCommand")));
                        return true;
                    }
                    int e = getVault(target, vaultType);
                    try{
                        if (e + Integer.parseInt(args[3]) >= 0 || e + Integer.parseInt(args[3]) < Integer.MAX_VALUE){
                            setVault(target, vaultType, e + Integer.parseInt(args[3]));
                            sender.sendMessage(EP.HandlePlaceholderAPI(target, Objects.requireNonNull(EP.languageCFG.getString("Vault.AddPlayerVault"))));
                        }
                    }catch (NumberFormatException numberFormatException){
                        sender.sendMessage(Objects.requireNonNull(EP.languageCFG.getString("Vault.OutOfInteger")));
                    }
                    break;
                case "take":
                    if (args.length != 4){
                        sender.sendMessage(Objects.requireNonNull(EP.languageCFG.getString("InvalidCommand")));
                        return true;
                    }
                    int f = getVault(target, vaultType);
                    try{
                        if (f - Integer.parseInt(args[3]) >= 0 || f - Integer.parseInt(args[3]) < Integer.MAX_VALUE){
                            setVault(target, vaultType, f - Integer.parseInt(args[3]));
                            sender.sendMessage(EP.HandlePlaceholderAPI(target, Objects.requireNonNull(EP.languageCFG.getString("Vault.TakePlayerVault"))));
                        }
                    }catch (NumberFormatException numberFormatException){
                        sender.sendMessage(Objects.requireNonNull(EP.languageCFG.getString("Vault.OutOfInteger")));
                    }
                    break;
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1){
            list = Arrays.asList("get", "set", "add", "take");
        }
        if (args.length == 2){
            list = null;
        }
        if (args.length == 3){
            list = Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("EssentialPlugin")).getConfig().getStringList("VaultType");
        }
        return list;
    }
}
