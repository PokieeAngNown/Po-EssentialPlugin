package cn.PoStudio;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.io.File;
import java.util.List;

public class ServerListPing implements Listener {

    @EventHandler
    public static void onServerPing(ServerListPingEvent serverListPingEvent){
        List<String> stringList = EssentialPluginAPI.getPlugin().getConfig().getStringList("MOTD");
        String string = String.join("\n", stringList);
        serverListPingEvent.setMotd(string);

        String iconString = EssentialPluginAPI.getPlugin().getConfig().getString("ServerIcon");
        assert iconString != null;
        try {
            serverListPingEvent.setServerIcon(Bukkit.loadServerIcon(new File(EssentialPluginAPI.getPlugin().getDataFolder().getPath(), iconString)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        int maxPlayerInteger = EssentialPluginAPI.getPlugin().getConfig().getInt("MaxPlayerDisplay");
        serverListPingEvent.setMaxPlayers(maxPlayerInteger);
    }
}
