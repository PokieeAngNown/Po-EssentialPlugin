package cn.PoStudio;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class EP extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public void onLoad(){
        // Plugin load logic

    }
}
