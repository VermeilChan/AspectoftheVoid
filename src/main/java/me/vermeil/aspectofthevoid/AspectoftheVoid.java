package me.vermeil.aspectofthevoid;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AspectoftheVoid extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(getCommand("giveaspectofthevoid")).setExecutor((sender, command, label, args) -> {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.isOp()) {
                    giveAspectOfTheVoid(player);
                }
            }
            return true;
        });
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
        if (event.getHand() != EquipmentSlot.HAND) {
            return false;
        }

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return false;
        }

        ItemStack itemInHand = event.getPlayer().getInventory().getItemInMainHand();

        if (itemInHand.getType() != Material.DIAMOND_SHOVEL) {
            return false;
        }

        ItemMeta meta = itemInHand.getItemMeta();
        if (meta == null) {
            return false;
        }

        String expectedName = ChatColor.GOLD + "Warped Aspect Of The Void";
        return meta.getDisplayName().equals(expectedName);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            if (isAspectOfTheVoid(itemInHand)) {
                event.setDamage(315);
            }
        }
    }

    private boolean isAspectOfTheVoid(ItemStack item) {
        if (item == null) {
            return false;
        }
        if (item.getType() != Material.DIAMOND_SHOVEL) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        return meta != null && meta.getDisplayName().equals(ChatColor.GOLD + "Warped Aspect Of The Void");
    }

    private void teleportPlayer(Player player) {
        Vector direction = player.getLocation().getDirection().normalize();
        Location teleportDestination = findTeleportDestination(player, direction);

        player.teleport(teleportDestination);

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 5));
    }

    private void teleportToTargetedBlock(Player player) {
        Block targetBlock = player.getTargetBlockExact(61, FluidCollisionMode.NEVER);

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
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 5));
        } else {
            player.sendMessage(ChatColor.RED + "Too far away, Try targeting a block within 61 blocks.");
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

    private void giveAspectOfTheVoid(Player player) {
        ItemStack item = new ItemStack(Material.DIAMOND_SHOVEL);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(ChatColor.GOLD + "Warped Aspect Of The Void");
            List<String> lore = Arrays.asList(
                    // Credit to (https://github.com/zSkillCode)

                    ChatColor.GRAY + "Damage: " + ChatColor.RED + "+315 " + ChatColor.YELLOW + "(+30) " + ChatColor.BLUE + "(+165)",
                    ChatColor.GRAY + "Strength: " + ChatColor.RED + "+300 " + ChatColor.YELLOW + "(+30) " + ChatColor.GOLD + "[+5] " + ChatColor.BLUE + "(+165)",
                    ChatColor.GRAY + "Crit Damage: " + ChatColor.RED + "+70",
                    ChatColor.GRAY + "Intelligence: " + ChatColor.GREEN + "+167 " + ChatColor.BLUE + "(+150) " + ChatColor.LIGHT_PURPLE + "(+17)",
                    ChatColor.DARK_PURPLE + "[" + ChatColor.AQUA + "✎" + ChatColor.DARK_PURPLE + "]",
                    "",
                    ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Ultimate Wise V",
                    "",
                    ChatColor.GOLD + "Ability: Instant Transmission" + ChatColor.YELLOW + " " + ChatColor.BOLD + "RIGHT CLICK",
                    ChatColor.GRAY + "Teleport 12 Blocks ahead of you and",
                    ChatColor.GRAY + "gain" + ChatColor.GREEN + " +50" + ChatColor.WHITE + " ✦ Speed" + ChatColor.GRAY + " for" + ChatColor.GREEN + " 3 seconds" + ChatColor.GRAY + ".",
                    ChatColor.DARK_GRAY + "Mana Cost: " + ChatColor.DARK_AQUA + "21",
                    "",
                    ChatColor.GOLD + "Ability: Ether Transmission" + ChatColor.YELLOW + " " + ChatColor.BOLD + "SNEAK RIGHT CLICK",
                    ChatColor.GRAY + "Teleport to your targeted block up",
                    ChatColor.GRAY + "to " + ChatColor.GREEN +"61 blocks " + ChatColor.GRAY + "away.",
                    ChatColor.DARK_GRAY + "Soulflow: " + ChatColor.DARK_AQUA + "1",
                    ChatColor.DARK_GRAY + "Mana Cost: " + ChatColor.DARK_AQUA + "81",
                    "",
                    ChatColor.GOLD + "" + ChatColor.MAGIC + "A" + ChatColor.RESET + ChatColor.GOLD + ChatColor.BOLD + " LEGENDARY SWORD " + ChatColor.MAGIC + "A"
            );
            meta.setLore(lore);
            item.setItemMeta(meta);
        }

        player.getInventory().addItem(item);
    }
}
