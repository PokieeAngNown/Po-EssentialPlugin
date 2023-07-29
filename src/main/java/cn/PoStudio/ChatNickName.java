package cn.PoStudio;

import cn.PoStudio.EntityNBT.handleNBT;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChatNickName {
    static final NamespacedKey prefixKey = new NamespacedKey(EssentialPluginAPI.getPlugin(), "PREFIX");
    static final NamespacedKey suffixKey = new NamespacedKey(EssentialPluginAPI.getPlugin(), "SUFFIX");
    static final File nickFile = new File(EssentialPluginAPI.getPlugin().getDataFolder().getPath(), "nickList.yml");
    private static final FileConfiguration nickFileCFG = YamlConfiguration.loadConfiguration(nickFile);

    public static void NickPlayer(Player player, NickType nickType, String nickString){

        if (nickType == NickType.PREFIX){
            handleNBT.setNBT(player, prefixKey, nickString);
        }
        if (nickType == NickType.SUFFIX){
            handleNBT.setNBT(player, suffixKey, nickString);
        }
    }

    public static String getPlayerPrefix(Player player){
        if (handleNBT.getNBT(player, prefixKey) == null){
            return "";
        }else{
            return (String) handleNBT.getNBT(player, prefixKey);
        }
    }

    public static String getPlayerSuffix(Player player){
        if (handleNBT.getNBT(player, suffixKey) == null){
            return "";
        }else{
            return (String) handleNBT.getNBT(player, suffixKey);
        }
    }

    public static void createNick(NickType nickType, String nickName, String nickString){
        /*
            Prefix:
                nickName1: nickString1
         */
        if (nickType == NickType.PREFIX){
            nickFileCFG.set("Prefix." + nickName, nickString);
        }
        if (nickType == NickType.SUFFIX){
            nickFileCFG.set("Suffix." + nickName, nickString);
        }
        try {
            nickFileCFG.save(nickFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeNick(NickType nickType, String nickName){
        if (nickType == NickType.PREFIX){
            nickFileCFG.set("Prefix." + nickName, null);
        }
        if (nickType == NickType.SUFFIX){
            nickFileCFG.set("Suffix." + nickName, null);
        }
        try {
            nickFileCFG.save(nickFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getNickList(NickType nickType){
        List<String> nickList = new ArrayList<>(nickFileCFG.getKeys(true));
        nickList.remove("Prefix");
        nickList.remove("Suffix");
        List<String> realList = new ArrayList<>();
        String[] strings;
        for (String s : nickList) {
            strings = s.split("\\.");
            if (nickType == NickType.PREFIX){
                if (strings[0].equals("Prefix")){
                    realList.add(strings[1]);
                }
            }
            if (nickType == NickType.SUFFIX){
                if (strings[0].equals("Suffix")){
                    realList.add(strings[1]);
                }
            }
        }
        return realList;
    }

    public static String getNick(NickType nickType, String nickName){
        if (nickType == NickType.PREFIX){
            if (isNickStore(nickType, nickName)){
                return nickFileCFG.getString("Prefix." + nickName);
            }
        }
        if (nickType == NickType.SUFFIX){
            if (isNickStore(nickType, nickName)){
                return nickFileCFG.getString("Suffix." + nickName);
            }
        }
        return "";
    }

    public static boolean isNickStore(NickType nickType, String nickName){
        if (nickType == NickType.PREFIX){
            String nickString = nickFileCFG.getString("Prefix." + nickName);
            return nickString != null;
        }
        if (nickType == NickType.SUFFIX){
            String nickString = nickFileCFG.getString("Suffix." + nickName);
            return nickString != null;
        }
        return false;
    }
    public enum NickType{
        PREFIX,
        SUFFIX
    }


}
