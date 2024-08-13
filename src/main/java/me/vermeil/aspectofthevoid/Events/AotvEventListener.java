package me.vermeil.aspectofthevoid.Events;

import me.vermeil.aspectofthevoid.Utils.ColorUtils;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AotvEventListener implements Listener {

    private static final int TELEPORT_DISTANCE = 12;
    private static final int MAX_TARGET_BLOCK_DISTANCE = 61;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) && isValidItem(item)) {
            if (player.isSneaking()) {
                teleportToTargetedBlock(player);
            } else {
                teleportPlayer(player);
            }
        }
    }

    private boolean isValidItem(ItemStack item) {
        if (item == null) return false;
        ItemMeta meta = item.getItemMeta();
        return meta != null && ColorUtils.color("&6Warped Aspect Of The Void").equals(meta.getDisplayName());
    }

    private void teleportPlayer(Player player) {
        Vector direction = player.getLocation().getDirection().normalize();
        Location destination = player.getLocation().add(direction.multiply(TELEPORT_DISTANCE));
        while (!destination.getBlock().isPassable()) {
            destination.subtract(direction.clone().multiply(0.5));
        }
        setPlayerLocation(player, destination);
        playTeleportEffects(player);
    }

    private void teleportToTargetedBlock(Player player) {
        Block targetBlock = player.getTargetBlockExact(MAX_TARGET_BLOCK_DISTANCE, FluidCollisionMode.NEVER);
        if (targetBlock != null) {
            Location destination = targetBlock.getLocation().add(0.5, 1, 0.5);
            setPlayerLocation(player, destination);
            playTeleportEffects(player);
        } else {
            player.sendMessage(ColorUtils.color("&cToo far away, try targeting a block within &6" + MAX_TARGET_BLOCK_DISTANCE + " &cblocks."));
        }
    }

    private void setPlayerLocation(Player player, Location location) {
        float yaw = player.getLocation().getYaw();
        float pitch = player.getLocation().getPitch();
        player.teleport(location);
        Location correctedLocation = player.getLocation();
        correctedLocation.setYaw(yaw);
        correctedLocation.setPitch(pitch);
        player.teleport(correctedLocation);
    }

    private void playTeleportEffects(Player player) {
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 5));
    }
}
