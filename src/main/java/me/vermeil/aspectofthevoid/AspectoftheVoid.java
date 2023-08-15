package me.vermeil.aspectofthevoid;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class AspectoftheVoid extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getMaterial() == Material.DIAMOND_SPADE && event.getAction().toString().contains("RIGHT_CLICK")) {
            event.setCancelled(true);

            Vector direction = player.getLocation().getDirection().normalize().multiply(5);
            player.setVelocity(direction);

            player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 1.0f);
        }
    }
}
