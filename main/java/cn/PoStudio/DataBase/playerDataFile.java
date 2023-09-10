package cn.PoStudio.DataBase;

import cn.PoStudio.EssentialPluginAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class playerDataFile {
    private static final String pluginDic = EssentialPluginAPI.getPlugin().getDataFolder().toString();
    public static boolean createPlayerFile(@NotNull UUID uuid){
        File file = new File (pluginDic + "/playerData", uuid + ".yml");
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void writePlayerData(@NotNull UUID uuid, String path, Object value){
        File file = new File (pluginDic + "/playerData", uuid + ".yml");
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
        fileConfiguration.set(path, value);
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isPlayerHasDataFile(@NotNull UUID uuid){
        File file = new File (pluginDic + "/playerData", uuid + ".yml");
        return file.exists();
    }

    public static void setDefaultData(UUID uuid){
        writePlayerData(uuid, "PlayerName", Objects.requireNonNull(Bukkit.getPlayer(uuid)).getName());
    }
}
