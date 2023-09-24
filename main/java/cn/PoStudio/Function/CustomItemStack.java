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

import java.io.File;
import java.util.*;

public class CustomItemStack {
    static String FileName;
    protected final static File file = new File(EssentialPluginAPI.getPlugin().getDataFolder().getPath(), FileName);
    protected final static FileConfiguration config = YamlConfiguration.loadConfiguration(file);


    private static class Item{
        private String name;
        private String id;
        private String displayName;
        private List<String> lore;
        //private Map<String,String> option = null;

        public String getName(){
            return name;
        }
        public String getId(){return id;}
        public String getDisplayName(){return displayName;}
        public List<String> getLore(){return lore;}
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

    public static @NotNull List<File> getItemFiles(){
        File f = new File(EssentialPluginAPI.getPlugin().getDataFolder() + "/itemStack");
        File[] fs = f.listFiles();
        assert fs != null;
        return Arrays.asList(fs);
    }

    public static @NotNull List<Item> getItems(String file){
        File f = new File(EssentialPluginAPI.getPlugin().getDataFolder() + "/itemStack", file);
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
        List<Item> list = new ArrayList<>();
        Set<String> set = cfg.getKeys(false);
        for (int i = 0; i < set.size(); i++) {
            list.add(getItem(file, new ArrayList<>(set).get(i)));
        }
        return list;
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
