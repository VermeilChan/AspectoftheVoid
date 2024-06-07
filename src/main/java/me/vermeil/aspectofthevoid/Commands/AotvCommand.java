package me.vermeil.aspectofthevoid.Commands;

import me.vermeil.aspectofthevoid.Builder.AotvBuilder;
import me.vermeil.aspectofthevoid.Utils.ColorUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("ALL")
public class AotvCommand implements CommandExecutor {

    public AotvCommand() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (!player.isOp()) {
            return true;
        }

        ItemStack Aspectofthevoid = AotvBuilder.giveAotv();
        player.getInventory().addItem(Aspectofthevoid);
        player.sendMessage(ColorUtils.color("&aYou have received the Aspect Of The Void."));

        return true;
    }
}