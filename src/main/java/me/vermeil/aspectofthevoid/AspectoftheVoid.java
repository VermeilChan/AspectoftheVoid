package me.vermeil.aspectofthevoid;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class AspectoftheVoid extends JavaPlugin implements Listener {

    private int teleportDistance;

    @Override
    public void onEnable() {
        saveDefaultConfig(); // Copies the config.yml from the JAR if it doesn't exist
        loadConfig();

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        loadConfig();
    }

    private void loadConfig() {
        FileConfiguration config = getConfig();
        teleportDistance = config.getInt("teleport_distance", 5);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getMaterial() == Material.DIAMOND_SPADE && event.getAction().toString().contains("RIGHT_CLICK")) {
            event.setCancelled(true);

            Vector direction = player.getLocation().getDirection().normalize().multiply(teleportDistance);
            player.setVelocity(direction);

            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 1.0f);
        }
    }
}
