package cn.PoStudio.Function;

import cn.PoStudio.EssentialPluginAPI;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CustomItemStack {
    static String FileName;
    protected final static File file = new File(EssentialPluginAPI.getPlugin().getDataFolder().getPath(), FileName);
    protected final static FileConfiguration config = YamlConfiguration.loadConfiguration(file);


    private static class Item{
        String name;
        String id;
        String displayName;
        List<String> lore;
        Map<String,String> option = null;

        public String getName(){
            return name;
        }
    }

    public static @NotNull Item getItem(String file, String itemID){
        FileName = file;
        Item item = new Item();
        item.name = itemID;
        item.id = config.getString(itemID + ".ID");
        item.displayName = config.getString(itemID + ".Name");
        item.lore = config.getStringList(itemID + ".Lore");
        return item;
    }

    public static @Nullable List<String> getItemFiles(){
        File f = new File(EssentialPluginAPI.getPlugin().getDataFolder() + "/itemStack");
        File[] fs = f.listFiles();
        List<String> sl = new ArrayList<>();
        if (fs == null){
            return null;
        }
        for (File value : fs) {
            sl.add(value.getName());
        }
        return sl;
    }

    public static @NotNull List<String> getItems(String file){
        File f = new File(EssentialPluginAPI.getPlugin().getDataFolder() + "/itemStack", file);
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
        return new ArrayList<>(cfg.getKeys(false));
    }

    public static void sendItemToPlayer(@NotNull Item item, @NotNull Player player){
        ItemStack itemStack = new ItemStack(Objects.requireNonNull(Material.matchMaterial(item.id)));
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(item.displayName);
        itemMeta.setLore(item.lore);
        itemStack.setAmount(1);
        itemStack.setItemMeta(itemMeta);



        Inventory inventory = player.getInventory();
        inventory.addItem(itemStack);
    }
}
