package cn.PoStudio;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RegPlaceholderAPI extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "POEP";
    }

    @Override
    public @NotNull String getAuthor() {
        return "PokieeAkuno_411";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0-Beta";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (params.equalsIgnoreCase("PlayerPrefix")){
            return ChatNickName.getPlayerPrefix((Player) player);
        }
        if (params.equalsIgnoreCase("PlayerSuffix")){
            return ChatNickName.getPlayerSuffix((Player) player);
        }
        return null;
    }
}
