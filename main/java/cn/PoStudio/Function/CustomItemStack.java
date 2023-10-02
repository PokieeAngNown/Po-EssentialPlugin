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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CustomItemStack {
    static String FileName = "";
    public static File file = new File(EssentialPluginAPI.getPlugin().getDataFolder() + "/itemStack", FileName);
    public static FileConfiguration config = YamlConfiguration.loadConfiguration(file);


    public static class CItem{
        private String name; //物品识别服
        private String id; //物品在mc内的物品展示
        private String displayName; //物品展示名称
        private List<String> lore; //物品段落行
        //private Map<String,String> option = null;

        public String getName(){
            return name;
        }
        public String getId(){return id;}
        public String getDisplayName(){return displayName;}
        public List<String> getLore(){return lore;}

        public void setName(String str){
            name = str;
        }
        public void setId(String str){
            id = str;
        }
        public void setDisplayName(String str){
            displayName = str;
        }
        public void setLore(List<String> strings){
            lore = strings;
        }
    }

    public static @NotNull CItem getItem(String fileName, String itemID){
        File f = new File(EssentialPluginAPI.getPlugin().getDataFolder() + "/itemStack", fileName);
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
        CItem item = new CItem();
        item.name = itemID;
        item.id = cfg.getString(itemID + ".ID");
        item.displayName = cfg.getString(itemID + ".Name");
        item.lore = cfg.getStringList(itemID + ".Lore");
        return item;
    }

    public static @NotNull List<File> getItemFiles(){
        File f = new File(EssentialPluginAPI.getPlugin().getDataFolder() + "/itemStack");
        File[] fs = f.listFiles();
        assert fs != null;
        List<File> file2 = new ArrayList<>();
        for (File value : fs) {
            if (value.getName().contains(".yml")) {
                file2.add(value);
            }
        }
        return file2;
    }

    public static @NotNull List<CItem> getItems(String fileName){
        File f = new File(EssentialPluginAPI.getPlugin().getDataFolder() + "/itemStack", fileName);
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
        List<CItem> list = new ArrayList<>();
        Set<String> set = cfg.getKeys(false);
        for (int i = 0; i < set.size(); i++) {
            list.add(getItem(fileName, new ArrayList<>(set).get(i)));
        }
        return list;
    }

    public static void sendItemToPlayer(@NotNull CItem item, @NotNull Player player){
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

    public static boolean isItemInFile(@NotNull String fileName, @NotNull String itemName){
        File f = new File(EssentialPluginAPI.getPlugin().getDataFolder() + "/itemStack", fileName);
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
        return cfg.getKeys(false).contains(itemName);
    }
}
