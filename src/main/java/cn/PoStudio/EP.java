package cn.PoStudio;

import cn.PoStudio.Command.CheckCommand;
import cn.PoStudio.Tab.CheckTab;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public final class EP extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        //插件硬前置检测
        ///PlaceholderAPI
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().warning("|||Po-EP| Can't find the depend: PlaceholderAPI. Please download and install");
            Bukkit.getPluginManager().disablePlugin(this);
        }else{
            getLogger().warning("|||Po-EP| Found the depend: PlaceholderAPI" + Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")).getDescription().getVersion());
        }
        ///
        //本插件启动检测
        if (Bukkit.getPluginManager().isPluginEnabled(this)){
            HandleDefaultFile();
        }
        //注册指令
        loadCommand();
        loadTab();
        getLogger().warning("|||Po-EP| Essential Plugin started!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().warning("|||Po-EP| Essential Plugin uninstalled!");
    }

    @Override
    public void onLoad(){
        // Plugin load logic
        createLanguageYamlFile();
        getLogger().warning("|||Po-EP| Essential Plugin on loading...");
    }



    public void createLanguageYamlFile(){
        this.saveResource("language/zh_CN.yml", false);
    }

    public void loadCommand(){
        Objects.requireNonNull(getCommand("check")).setExecutor(new CheckCommand());
    }
    public void loadTab(){
        Objects.requireNonNull(getCommand("check")).setTabCompleter(new CheckTab());
    }

    /*
        文件加载
     */
    public File languageF;
    public static FileConfiguration languageCFG;
    public void HandleDefaultFile(){
        languageF = new File(this.getDataFolder().getPath() + "/language", "zh_CN.yml");
        languageCFG = YamlConfiguration.loadConfiguration(languageF);
    }
}
