package me.vermeil.aspectofthevoid;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class AspectoftheVoid extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!isRightClick(event)) {
            return;
        }

        Player player = event.getPlayer();

        if (player.isSneaking()) {
            teleportToTargetedBlock(player);
        } else {
            teleportPlayer(player);
        }
    }

    private boolean isRightClick(PlayerInteractEvent event) {
        return event.getPlayer().getInventory().getItemInMainHand().getType() == Material.DIAMOND_SHOVEL
                && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK);
    }

    private void teleportPlayer(Player player) {
        Vector direction = player.getLocation().getDirection().normalize();
        Location teleportDestination = findTeleportDestination(player, direction);

        player.teleport(teleportDestination);

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 5));
    }

    private void teleportToTargetedBlock(Player player) {
        Block targetBlock = player.getTargetBlockExact(57, FluidCollisionMode.NEVER);

        if (targetBlock != null) {
            Location teleportDestination = targetBlock.getLocation().add(0.5, 1, 0.5);

            float yaw = player.getLocation().getYaw();
            float pitch = player.getLocation().getPitch();

            player.teleport(teleportDestination);
            Location correctedLocation = player.getLocation();
            correctedLocation.setYaw(yaw);
            correctedLocation.setPitch(pitch);
            player.teleport(correctedLocation);

            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 5));
        } else {
            player.sendMessage(ChatColor.RED + "Too far away, Try targeting a block within 57 blocks.");
        }
    }

    private Location findTeleportDestination(Player player, Vector direction) {
        Location teleportDestination = player.getLocation();
        for (double i = 0; i < 12; i += 0.5) {
            Location loc = player.getLocation().add(direction.clone().multiply(i));
            if (!loc.getBlock().isPassable()) {
                break;
            }
            teleportDestination = loc;
        }
        return teleportDestination;
    }
}
