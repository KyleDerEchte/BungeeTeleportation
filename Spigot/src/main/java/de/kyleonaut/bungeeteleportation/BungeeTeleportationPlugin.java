package de.kyleonaut.bungeeteleportation;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.kyleonaut.bungeeteleportation.model.TeleportRequest;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.IOException;

/**
 * @author kyleonaut
 */
public class BungeeTeleportationPlugin extends JavaPlugin implements PluginMessageListener {

    @Override
    public void onEnable() {
        getServer().getMessenger().registerIncomingPluginChannel( this, "BungeeTeleportation:teleport:request", this );
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeTeleportation:teleport:request")) {
            return;
        }
        final ObjectMapper mapper = new ObjectMapper();
        try {
            final TeleportRequest teleportRequest = mapper.readValue(message, TeleportRequest.class);
            final Player p = Bukkit.getPlayer(teleportRequest.getPlayer());
            final Player target = Bukkit.getPlayer(teleportRequest.getTarget());
            if (p == null || target == null) {
                return;
            }
            p.teleport(target);
            p.sendMessage("§8[§bBungeeTeleportation§8]§7 You were teleported.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
