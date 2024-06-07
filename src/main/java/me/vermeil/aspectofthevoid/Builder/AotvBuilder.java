package me.vermeil.aspectofthevoid.Builder;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import me.vermeil.aspectofthevoid.Utils.ColorUtils;
import org.bukkit.enchantments.Enchantment;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AotvBuilder {

    public static ItemStack giveAotv() {
        ItemStack Aspectofthevoid = new ItemStack(Material.DIAMOND_SHOVEL);
        ItemMeta meta = Aspectofthevoid.getItemMeta();

        Objects.requireNonNull(meta).setDisplayName(ColorUtils.color("&6Warped Aspect Of The Void"));

        List<String> lore = Arrays.asList(

                ColorUtils.color("&7Damage: &c+315 &e(+30) &9(+165)"),
                ColorUtils.color("&7Strength: &c+300 &e(+30) &6[+5] &9(+165)"),
                ColorUtils.color("&7Crit Damage: &c+70"),
                ColorUtils.color("&7Intelligence: &a+167 &9(+150) &d(+17)"),
                ColorUtils.color("&5[&b✎&5]"),
                "",
                ColorUtils.color("&d&lUltimate Wise V"),
                "",
                ColorUtils.color("&6Ability: Instant Transmission &eRIGHT CLICK"),
                ColorUtils.color("&7Teleport 12 Blocks ahead of you and"),
                ColorUtils.color("&7gain &a+50 &f✦ Speed &7for &a3 seconds&7."),
                ColorUtils.color("&8Mana Cost: &321"),
                "",
                ColorUtils.color("&6Ability: Ether Transmission &eSNEAK RIGHT CLICK"),
                ColorUtils.color("&7Teleport to your targeted block up"),
                ColorUtils.color("&7to &a61 blocks &7away."),
                ColorUtils.color("&8Soulflow: &381"),
                ColorUtils.color("&8Mana Cost: &381"),
                "",
                ColorUtils.color("&6&l&kA&a &6&lLEGENDARY SWORD &kA")
        );

        meta.setLore(lore);
        meta.addEnchant(Enchantment.UNBREAKING, 100, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        Aspectofthevoid.setItemMeta(meta);

        return Aspectofthevoid;
    }
}
