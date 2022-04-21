package de.kyleonaut.bungeeteleportation;

import net.md_5.bungee.api.plugin.Plugin;

/**
 * @author kyleonaut
 */
public class BungeeTeleportationPlugin extends Plugin {

    @Override
    public void onEnable() {
        getProxy().registerChannel("BungeeTeleportation:teleport:request");
    }
}
