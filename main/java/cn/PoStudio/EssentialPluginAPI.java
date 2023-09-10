package cn.PoStudio;

import cn.PoStudio.Event.JoinExitEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class EssentialPluginAPI extends JavaPlugin {
    public static Plugin getPlugin(){
        return Bukkit.getPluginManager().getPlugin("EssentialPlugin");
    }

    /*
        如果Object是String 则直接处理
        如果Object是List<String> 则用\n拼接
     */
    @NotNull
    public static String handleMessage(String key, OfflinePlayer papiPlayer){
        File languageFile = new File(EssentialPluginAPI.getPlugin().getDataFolder().getPath(), "language/" + EssentialPluginAPI.getPlugin().getConfig().getString("Language")+ ".yml");
        FileConfiguration languageFileCFG = YamlConfiguration.loadConfiguration(languageFile);
        List<String> stringList = languageFileCFG.getStringList(key);
        if (stringList.equals(new ArrayList<>())){
            return PlaceholderAPI.setPlaceholders(papiPlayer, Objects.requireNonNull(languageFileCFG.getString(key)));
        }else{
            return PlaceholderAPI.setPlaceholders(papiPlayer, String.join("\n", stringList));
        }
    }

    public static String handleListToString(List<String> list){
        return String.join("\n", list);
    }
    /*
        Main
     */

    @Override
    public void onEnable() {
        //插件硬前置检测
        ///PlaceholderAPI
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().info("Can't find the depend: PlaceholderAPI. Please download and install");
            Bukkit.getPluginManager().disablePlugin(this);
        }else{
            getLogger().info("Found the depend: PlaceholderAPI" + Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")).getDescription().getVersion());
            new RegPlaceholderAPI().register();
        }
        //本插件启动检测
        if (Bukkit.getPluginManager().isPluginEnabled(this)){
            getLogger().info("Essential Plugin started!");
        }
        // Plugin startup logic
        setupListener();
        setupCommand();
        setupTab();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Essential Plugin uninstalled!");
    }

    @Override
    public void onLoad(){
        // Plugin load logic
        createYamlFile();
        getLogger().info("Essential Plugin on loading...");
    }

    /*
        Plugin Setup
     */

    private void setupListener(){
        getPlugin().getServer().getPluginManager().registerEvents(new ServerListPing(), this);
        getPlugin().getServer().getPluginManager().registerEvents(new JoinExitEvent(), this);
    }

    private void setupCommand(){
    }

    private void setupTab(){

    }

    /*
        实体化jar包内文件
     */

    private void createYamlFile(){
        if (!new File(this.getDataFolder().getPath(), "config.yml").exists()) {
            this.saveResource("config.yml", true);
        }
        if (!new File(this.getDataFolder().getPath(), "Icon.png").exists()) {
            this.saveResource("Icon.png", true);
        }
        if (!new File(this.getDataFolder().getPath(), "nickList.yml").exists()) {
            this.saveResource("nickList.yml", true);
        }
        if (!new File(this.getDataFolder().getPath(), "language/zh_CN.yml").exists()) {
            this.saveResource("language/zh_CN.yml", true);
        }
        if (!new File(this.getDataFolder().getPath(), "playerData").mkdir()){
            getLogger().warning("Something wrong in Plugin. Plz report the wrong");
        }
    }
}
