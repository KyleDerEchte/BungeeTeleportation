package de.kyleonaut.bungeeteleportation.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.kyleonaut.bungeeteleportation.model.TeleportRequest;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * @author kyleonaut
 */
public class BungeeTeleportCommand extends Command {
    public BungeeTeleportCommand() {
        super("bungeeteleport", "BungeeTeleportation.command", "btp", "bungeetp");
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (args.length == 0) {
            commandSender.sendMessage(new TextComponent("§8[§bBungeeTeleportation§8]§c You must provide a player."));
            return;
        }
        if (!(commandSender instanceof final ProxiedPlayer player)) {
            commandSender.sendMessage(new TextComponent("§8[§bBungeeTeleportation§8]§c Only players are allowed to use this command."));
            return;
        }
        final ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
        if (target == null) {
            commandSender.sendMessage(new TextComponent("§8[§bBungeeTeleportation§8]§c The provided player is not online."));
            return;
        }
        final TeleportRequest teleportRequest = new TeleportRequest();
        teleportRequest.setPlayer(player.getUniqueId());
        teleportRequest.setTarget(player.getUniqueId());
        final ObjectMapper mapper = new ObjectMapper();
        try {
            final String stringTeleportRequest = mapper.writer(new DefaultPrettyPrinter()).writeValueAsString(teleportRequest);
            ByteArrayDataOutput data = ByteStreams.newDataOutput();
            data.writeUTF(stringTeleportRequest);
            player.getServer().getInfo().sendData("BungeeTeleportation:teleport:request", data.toByteArray());
            player.connect(target.getServer().getInfo());
            player.sendMessage(new TextComponent("§8[§bBungeeTeleportation§8]§7 You'll be teleported right now."));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
