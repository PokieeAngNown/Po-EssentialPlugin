package cn.PoStudio;

import cn.PoStudio.Hologram.mainHologram;
import cn.PoStudio.Vault.mainVault;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public final class EP extends JavaPlugin {

    public static FileConfiguration languageCFG;
    /*
        文件加载
     */
    public File languageF;

    public static String HandlePlaceholderAPI(Player player, String string){
        return PlaceholderAPI.setPlaceholders(player, string);
    }

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
            new RegPlaceholderAPI().register();
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
        createYamlFile();
        getLogger().warning("|||Po-EP| Essential Plugin on loading...");
    }

    public void createYamlFile(){
        this.saveResource("config.yml", false);
        this.saveResource("hologramList.yml", false);
        this.saveResource("language/zh_CN.yml", false);
    }

    public void loadCommand(){
        Objects.requireNonNull(getCommand("vault")).setExecutor(new mainVault());
        Objects.requireNonNull(getCommand("hologram")).setExecutor(new mainHologram());
    }

    public void loadTab(){
        Objects.requireNonNull(getCommand("vault")).setTabCompleter(new mainVault());
        Objects.requireNonNull(getCommand("hologram")).setTabCompleter(new mainHologram());
    }


    public void HandleDefaultFile(){
        String language = this.getConfig().getString("Language");
        languageF = new File(this.getDataFolder().getPath() + "/language", language + ".yml");
        languageCFG = YamlConfiguration.loadConfiguration(languageF);
    }
}
