package cn.PoStudio;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public final class EP extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        //插件硬前置检测
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().warning("|||Po-EP| Can't find the depend: PlaceholderAPI. Please download and install");
            Bukkit.getPluginManager().disablePlugin(this);
        }else{
            getLogger().warning("|||Po-EP| Found the depend: PlaceholderAPI" + Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")).getDescription().getVersion());
        }
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
        createLanguageJsonFile();
        getLogger().warning("|||Po-EP| Essential Plugin on loading...");
    }

    Plugin plugin = this;

    public void createLanguageJsonFile(){
        this.saveResource("language/zh_CN.json", false);
    }
}
