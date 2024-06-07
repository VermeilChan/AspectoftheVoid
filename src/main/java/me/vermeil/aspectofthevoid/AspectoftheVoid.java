package me.vermeil.aspectofthevoid;

import me.vermeil.aspectofthevoid.Commands.AotvCommand;
import me.vermeil.aspectofthevoid.Events.AotvEventListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class AspectoftheVoid extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new AotvEventListener(), this);
        Objects.requireNonNull(getCommand("giveaspectofthevoid")).setExecutor(new AotvCommand());
    }
}
