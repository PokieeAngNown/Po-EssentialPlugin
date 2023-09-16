package cn.PoStudio;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GiftPack {
    /*
        name:
            - "item::minecraft:*"
            - "money::101"
     */
    protected final static File file = new File(EssentialPluginAPI.getPlugin().getDataFolder().getPath(), "giftPack.yml");
    protected final static FileConfiguration config = YamlConfiguration.loadConfiguration(file);

    private static class Gift{
        String name;
        public @NotNull List<String> contain(){
            return config.getStringList(name);
        }
    }
    public static @NotNull Gift getGift (String giftName){
        Gift gift = new Gift();
        gift.name = giftName;
        return gift;
    }

    @Contract (" -> new")
    public static @NotNull List<String> getGiftList(){
        return new ArrayList<>(config.getKeys(false));
    }

    public static void sendGift(String giftName, Player player){
        Gift gift = getGift(giftName);
        List<String> giftList = gift.contain();
        for (String str : giftList) {
            if (str.contains("item")) {
                String item = str.split("::")[1];
                int amount = Integer.parseInt(str.split("::")[2]);
                ItemStack itemStack = new ItemStack(Objects.requireNonNull(Material.matchMaterial(item)), amount);
                Inventory inventory = player.getInventory();
                inventory.addItem(itemStack);
            }
        }
    }
}
