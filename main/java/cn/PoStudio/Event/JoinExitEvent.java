package cn.PoStudio.Event;

import cn.PoStudio.EssentialPluginAPI;
import cn.PoStudio.GiftPack;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static cn.PoStudio.DataBase.playerDataFile.*;

public class JoinExitEvent implements Listener {
    @EventHandler
    private void onPlayerJoin(@NotNull PlayerJoinEvent playerJoinEvent){
        String s;
        UUID playerUUID = playerJoinEvent.getPlayer().getUniqueId();
        if (playerJoinEvent.getPlayer().hasPlayedBefore()){
            //玩家之前玩过
            if (!isPlayerHasDataFile(playerUUID)) {
                createPlayerFile(playerUUID);
                setDefaultData(playerUUID);
            }

            s = EssentialPluginAPI.getPlugin().getConfig().getString("PlayerJoinMessage");
        }else{
            //玩家之前没玩过
            if (!isPlayerHasDataFile(playerUUID)) {
                createPlayerFile(playerUUID);
                setDefaultData(playerUUID);
            }

            s = EssentialPluginAPI.getPlugin().getConfig().getString("PlayerFirstJoinMessage");
            //玩家礼物
            Object object = EssentialPluginAPI.getPlugin().getConfig().get("PlayerFirstJoinGift");
            if (object != null){
                GiftPack.sendGift(object.toString(), playerJoinEvent.getPlayer());
            }
        }
        assert s != null;
        playerJoinEvent.setJoinMessage(PlaceholderAPI.setPlaceholders(playerJoinEvent.getPlayer(), s));
    }
    @EventHandler
    private void onPlayerExit(@NotNull PlayerQuitEvent playerQuitEvent){
        String s;
        s = EssentialPluginAPI.getPlugin().getConfig().getString("PlayerQuitMessage");
        assert s != null;
        playerQuitEvent.setQuitMessage(PlaceholderAPI.setPlaceholders(playerQuitEvent.getPlayer(), s));
    }
}
