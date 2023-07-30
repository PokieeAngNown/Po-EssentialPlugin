package cn.PoStudio;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;

public class PlayerEvent implements Listener {

    private String getMessage(Player player,Behaviour behaviour, String permissionType){
        if (behaviour == Behaviour.Join){
            if (player.hasPermission(new Permission("joinmessage." + permissionType))){
                return EssentialPluginAPI.getPlugin().getConfig().getString("JoinMessage." + permissionType);
            }
        }
        if (behaviour == Behaviour.Exit){
            if (player.hasPermission(new Permission("exitmessage." + permissionType))){
                return EssentialPluginAPI.getPlugin().getConfig().getString("ExitMessage." + permissionType);
            }
        }
        return "";
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent playerJoinEvent){
        Player player = playerJoinEvent.getPlayer();
        //
    }


    public enum Behaviour {
        Join, Exit
    }
}
