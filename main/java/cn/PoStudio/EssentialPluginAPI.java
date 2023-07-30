package cn.PoStudio;

import cn.PoStudio.Command.NickPlayerCommand;
import me.clip.placeholderapi.PlaceholderAPI;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
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
    public static String handleMessage(String key, Player papiPlayer){
        File languageFile = new File(EssentialPluginAPI.getPlugin().getDataFolder().getPath(), "language/" + EssentialPluginAPI.getPlugin().getConfig().getString("Language")+ ".yml");
        FileConfiguration languageFileCFG = YamlConfiguration.loadConfiguration(languageFile);
        List<String> stringList = languageFileCFG.getStringList(key);
        if (stringList.equals(new ArrayList<>())){
            return PlaceholderAPI.setPlaceholders(papiPlayer, Objects.requireNonNull(languageFileCFG.getString(key)));
        }else{
            return PlaceholderAPI.setPlaceholders(papiPlayer, String.join("\n", stringList));
        }

    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        setupListener();
        setupCommand();
        setupTab();
        //插件硬前置检测
        ///PlaceholderAPI
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().info("Can't find the depend: PlaceholderAPI. Please download and install");
            Bukkit.getPluginManager().disablePlugin(this);
        }else{
            getLogger().info("Found the depend: PlaceholderAPI" + Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")).getDescription().getVersion());
            new RegPlaceholderAPI().register();
        }
        ///LuckPerm
        if (Bukkit.getPluginManager().getPlugin("LuckPerm") == null) {
            getLogger().info("Can't find the depend: LuckPerm. Please download and install");
            Bukkit.getPluginManager().disablePlugin(this);
        }else{
            getLogger().info("Found the depend: LuckPerm" + Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("LuckPerm")).getDescription().getVersion());
            RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
            if (provider != null) {
                LuckPerms api = provider.getProvider();

            }
        }
        //本插件启动检测
        if (Bukkit.getPluginManager().isPluginEnabled(this)){
            getLogger().info("Essential Plugin started!");
        }
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

    private void setupListener(){
        getPlugin().getServer().getPluginManager().registerEvents(new ServerListPing(), this);
        getPlugin().getServer().getPluginManager().registerEvents(new Chat(), this);
    }

    private void setupCommand(){
        Objects.requireNonNull(this.getCommand("nick")).setExecutor(new NickPlayerCommand());
    }

    private void setupTab(){
        Objects.requireNonNull(this.getCommand("nick")).setTabCompleter(new NickPlayerCommand());
    }

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
    }

}
