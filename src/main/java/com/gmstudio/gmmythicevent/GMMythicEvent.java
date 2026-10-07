package com.gmstudio.gmmythicevent;

import com.gmstudio.gmmythicevent.listener.MechanicListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class GMMythicEvent extends JavaPlugin {

    private static GMMythicEvent instance;
    private List<String> immuneMobs = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;
        
        // Load config
        saveDefaultConfig();
        loadConfig();

        // Register the mechanic listener
        getServer().getPluginManager().registerEvents(new MechanicListener(), this);

        getLogger().info("GMMythicevent has been enabled!");
        getLogger().info("Loaded " + immuneMobs.size() + " immune skill mobs from config.");
    }

    @Override
    public void onDisable() {
        getLogger().info("GMMythicevent has been disabled.");
    }

    public void loadConfig() {
        reloadConfig();
        immuneMobs = getConfig().getStringList("immune-skill-mobs");
    }

    public static GMMythicEvent getInstance() {
        return instance;
    }

    public List<String> getImmuneMobs() {
        return immuneMobs;
    }
}
