package cn.PoStudio;

import cn.PoStudio.Vault.mainVault;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class RegPlaceholderAPI extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "Po-EssentialPlugin";
    }

    @Override
    public @NotNull String getAuthor() {
        return "PokieeAkuno_411";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0.9-SNAPSHOT";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (params.equalsIgnoreCase("PlayerMoney")){ //%Po-EssentialPlugin_PlayerVault%
            return String.valueOf(mainVault.getVault(player.getPlayer(), "money"));
        }
        return null;
    }
}
