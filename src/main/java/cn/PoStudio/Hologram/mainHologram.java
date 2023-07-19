package cn.PoStudio.Hologram;

import cn.PoStudio.EP;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class mainHologram implements CommandExecutor, TabCompleter {
    @NotNull
    static Plugin plugin = Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("EssentialPlugin"));
    static File file = new File(plugin.getDataFolder().getPath(), "hologramList.yml");
    static FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);

    @NotNull //Boolean 创建全息视图成功返回true
    public static Boolean createHologram(@NotNull Location location, String name){
        World world = location.getWorld();
        assert world != null;
        TextDisplay textDisplay = world.spawn(location, TextDisplay.class);
        if (!fileConfiguration.isSet("List." + name)){
            fileConfiguration.set("List." + name, null);
            String mainString = "List." + name;
            fileConfiguration.set(mainString + ".UUID", textDisplay.getUniqueId().toString());
            fileConfiguration.set(mainString + ".Location.World", Objects.requireNonNull(textDisplay.getLocation().getWorld()).getName());
            fileConfiguration.set(mainString + ".Location.X", textDisplay.getLocation().getX());
            fileConfiguration.set(mainString + ".Location.Y", textDisplay.getLocation().getY());
            fileConfiguration.set(mainString + ".Location.Z", textDisplay.getLocation().getZ());
            try {
                fileConfiguration.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    @NotNull //Boolean 删除全息视图成功返回true
    public static Boolean removeHologram(String name){
        if (fileConfiguration.isSet("List." + name)){
            UUID uuid = getUUID(name);
            if (Bukkit.getEntity(uuid) != null) {
                Entity entity = Bukkit.getEntity(uuid);
                assert entity != null;
                entity.remove();
            }
            fileConfiguration.set("List." + name, null);
            try {
                fileConfiguration.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    @NotNull //Location 返回全息视图坐标
    public static Location getLocation(String name){
        World world = Bukkit.getWorld(Objects.requireNonNull(fileConfiguration.getString("List." + name + ".Location.World")));
        double X = Double.parseDouble(Objects.requireNonNull(fileConfiguration.getString("List." + name + ".Location.X")));
        double Y = Double.parseDouble(Objects.requireNonNull(fileConfiguration.getString("List." + name + ".Location.Y")));
        double Z = Double.parseDouble(Objects.requireNonNull(fileConfiguration.getString("List." + name + ".Location.Z")));
        return new Location(world, X, Y, Z);
    }

    @NotNull //UUID 返回全息视图UUID
    public static UUID getUUID(String name){
        return UUID.fromString(Objects.requireNonNull(fileConfiguration.getString("List." + name + ".UUID")));
    }

    public static Boolean isSetHolo(String name){
        return fileConfiguration.isSet("List." + name);
    }

    @Nullable
    public static List<String> readText(@NotNull TextDisplay textDisplay){
        String string = textDisplay.getText();
        if (string != null){
            return new ArrayList<>(Arrays.asList(string.split("\n")));
        }
        return null;
    }

    public static void writeText(@NotNull TextDisplay textDisplay, List<String> list){
        String string = String.join("\n", list);
        textDisplay.setText(string);
    }

    //根据config.yml的设置初始化全息视图
    public void setupTextDisplay(TextDisplay textDisplay){
        if (plugin.getConfig().isSet("Hologram.LineWidth")){
            textDisplay.setLineWidth(plugin.getConfig().getInt("Hologram.LineWidth"));
        }
        if (plugin.getConfig().isSet("Hologram.Alignment")){
            switch (Objects.requireNonNull(plugin.getConfig().getString("Hologram.Alignment"))){
                case "center": textDisplay.setAlignment(TextDisplay.TextAlignment.CENTER); break;
                case "left": textDisplay.setAlignment(TextDisplay.TextAlignment.LEFT); break;
                case "right": textDisplay.setAlignment(TextDisplay.TextAlignment.RIGHT); break;
            }
        }
        if (plugin.getConfig().isSet("Hologram.Shadow")){
            textDisplay.setShadowed(plugin.getConfig().getBoolean("Hologram.Shadow"));
        }
        if (plugin.getConfig().isSet("Hologram.Billboard")){
            switch (Objects.requireNonNull(plugin.getConfig().getString("Hologram.Billboard"))){
                case "fixed": textDisplay.setBillboard(Display.Billboard.FIXED); break;
                case "vertical": textDisplay.setBillboard(Display.Billboard.VERTICAL); break;
                case "horizontal": textDisplay.setBillboard(Display.Billboard.HORIZONTAL); break;
                case "center": textDisplay.setBillboard(Display.Billboard.CENTER); break;
            }
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String string, @NotNull String[] args){
        Player sender;
        TextDisplay textDisplay;
        String holoName = args[1];
        if (commandSender instanceof Player){
            sender = (Player) commandSender;
            switch (args[0]) {
                // :/hologram create [HoloName] (Line)
                case "create":
                    if (createHologram(sender.getLocation(), holoName)) {
                        UUID uuid = getUUID(holoName);
                        if (Bukkit.getEntity(uuid) instanceof TextDisplay) {
                            textDisplay = (TextDisplay) Bukkit.getEntity(uuid);
                            assert textDisplay != null;
                            if (args.length == 2) {
                                textDisplay.setText("§b§l全息视图-" +args[1]);
                            } else if (args.length == 3) {
                                textDisplay.setText(args[2]);
                            }
                            setupTextDisplay(textDisplay);
                            sender.sendMessage(Objects.requireNonNull(EP.languageCFG.getString("Hologram.CreateSuccess")));
                        }
                    }else{
                        sender.sendMessage(Objects.requireNonNull(EP.languageCFG.getString("Hologram.DuplicatedName")));
                    }
                    break;
                // :/hologram delete [HoloName]
                case "delete":
                    if (removeHologram(holoName)){
                        sender.sendMessage(Objects.requireNonNull(EP.languageCFG.getString("Hologram.RemoveSuccess")));
                    }else{
                        sender.sendMessage(Objects.requireNonNull(EP.languageCFG.getString("Hologram.HologramNotFound")));
                    }
                    break;
                // :/hologram addline [HoloName] [Line]
                case "addline":
                    if (isSetHolo(holoName)){
                        textDisplay = (TextDisplay) Bukkit.getEntity(getUUID(args[1]));
                        assert textDisplay != null;
                        List<String> list = readText(textDisplay);
                        assert list != null;
                        list.add(args[2]);
                        writeText(textDisplay, list);
                    }else{
                        sender.sendMessage(Objects.requireNonNull(EP.languageCFG.getString("Hologram.HologramNotFound")));
                    }
                    break;
                case "removeline":
                    if (isSetHolo(holoName)){
                        textDisplay = (TextDisplay) Bukkit.getEntity(getUUID(args[1]));
                        assert textDisplay != null;
                        List<String> list = readText(textDisplay);
                    }else{

                    }
            }
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1){
            list = Arrays.asList("create", "delete", "addline", "removeline");
        }
        if (args.length == 2){
            if (!args[0].equalsIgnoreCase("create")){
                Set<String> set = fileConfiguration.getKeys(true);
                List<String> dList = new ArrayList<>(set);
                for (String s : dList) {
                    if (s.contains(".")){
                        String[] strings = s.split("\\.");
                        list.add(strings[1]);
                    }
                }
            }
        }
        return list;
    }
}
