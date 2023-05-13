package com.fabledclan;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.fabledclan.Commands.CommandClass;
import com.fabledclan.Registers.AbilityRegistry;
import com.fabledclan.Registers.CommandRegistry;
import com.fabledclan.Registers.CustomBlockRegistry;
import com.fabledclan.Registers.CustomItemRegistry;
import com.fabledclan.Registers.EventRegistry;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;

public class Main extends JavaPlugin {
    private FileConfiguration config;
    private static Main instance;
    private Map<EntityType, EnemyData> enemyDataCache = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getLogger().info("Plugin enabled!");

        DatabaseManager.initDatabase();
        
        saveDefaultConfig();

        CustomBlockRegistry.initializeBlocks();
        CustomItemRegistry.initializeItems();
        AbilityRegistry.initializeAbilities();
        CustomRecipes.addRecipes();

        initializeListeners();

        initializeCommands();

        // Populate the cache when the server starts
        populateEnemyDataCache();
    }

    public static Plugin getPlugin() {
        return Bukkit.getPluginManager().getPlugins()[0];
    }

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Plugin disabled!");
    }

    private void initializeListeners() {
        EventRegistry.init();
        for (Listener listener : EventRegistry.getListeners()) {
            getServer().getPluginManager().registerEvents(listener, getPlugin());
        }
    }

    private void initializeCommands() {
        CommandRegistry.init();
        for (CommandClass command : CommandRegistry.getCommands()) {
            getCommand(command.getName()).setExecutor(command);
        }
    }

    public BarColor getBossBarColor(double currentHealth, double maxHealth) {
        double healthPercentage = (currentHealth / maxHealth) * 100;

        if (healthPercentage > 50) {
            return BarColor.GREEN;
        } else if (healthPercentage > 15) {
            return BarColor.YELLOW;
        } else {
            return BarColor.RED;
        }
    }

    public BarStyle getBossBarStyle() {
        return BarStyle.SOLID;
    }

    public FileConfiguration getPluginConfig() {
        return config;
    }

    public void savePluginConfig() {
        saveConfig();
    }

    public void reloadPluginConfig() {
        reloadConfig();
        config = getConfig();
    }

    private void populateEnemyDataCache() {
        for (EntityType entityType : EntityType.values()) {
            EnemyData enemyData = DatabaseManager.getEnemyData(entityType.name());
            if (enemyData != null) {
                enemyDataCache.put(entityType, enemyData);
            }
        }
    }

    public EnemyData getCachedEnemyData(EntityType entityType) {
        return enemyDataCache.get(entityType);
    }

}
