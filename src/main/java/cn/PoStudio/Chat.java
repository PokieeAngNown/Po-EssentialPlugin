package cn.PoStudio;

import cn.PoStudio.EntityNBT.handleNBT;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.permissions.Permission;

public class Chat implements Listener {

    public static String Name;
    public static String Message;

    @EventHandler
    private void onPlayerChat(AsyncPlayerChatEvent asyncPlayerChatEvent){
         Player player = asyncPlayerChatEvent.getPlayer();
         asyncPlayerChatEvent.setCancelled(!player.hasPermission(new Permission("essentialplugin.chat.default")));

         String format = EssentialPluginAPI.getPlugin().getConfig().getString("Chat.Format");
         assert format != null;

         Name = asyncPlayerChatEvent.getPlayer().getDisplayName();
         Message = asyncPlayerChatEvent.getMessage();
         boolean papi = EssentialPluginAPI.getPlugin().getConfig().getBoolean("Chat.PlaceHolderAPI");
         if (papi){
             asyncPlayerChatEvent.setMessage(PlaceholderAPI.setPlaceholders(player, asyncPlayerChatEvent.getMessage()));
             asyncPlayerChatEvent.setFormat(asyncPlayerChatEvent.getMessage());
         }
         if (handleNBT.hasNBT(player, new NamespacedKey(EssentialPluginAPI.getPlugin(), "PREFIX"))){
             String prefix = (String) handleNBT.getNBT(player, new NamespacedKey(EssentialPluginAPI.getPlugin(), "PREFIX"));
             format = format.replace("{POEP_PlayerPrefix}", prefix);
         }else{
             format = format.replace("{POEP_PlayerPrefix}", "");
         }
         if (handleNBT.hasNBT(player, new NamespacedKey(EssentialPluginAPI.getPlugin(), "SUFFIX"))){
             String suffix = (String) handleNBT.getNBT(player, new NamespacedKey(EssentialPluginAPI.getPlugin(), "SUFFIX"));
             format = format.replace("{POEP_PlayerSuffix}", suffix);
         }else{
             format = format.replace("{POEP_PlayerSuffix}", "");
         }
         if (format.contains(".DisplayName.") && format.contains(".Message.")){
             format = format.replaceAll(".DisplayName.", "%s").replaceAll(".Message.", "%s");
             asyncPlayerChatEvent.setFormat(format);
         }else{
             asyncPlayerChatEvent.setFormat("[%s] %s");
         }
    }
}
