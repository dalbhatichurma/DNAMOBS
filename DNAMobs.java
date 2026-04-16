package com.dnamobs;

import com.dnamobs.commands.DNACommand;
import com.dnamobs.listeners.MobCombatListener;
import com.dnamobs.listeners.MobSpawnListener;
import com.dnamobs.managers.DNAManager;
import com.dnamobs.managers.EvolutionManager;
import com.dnamobs.storage.DataStorage;
import com.dnamobs.storage.YAMLStorage;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * DNAMobs - Dynamic Mob Evolution System
 * Author: MathTeacher
 * 
 * Main plugin class that initializes all systems and managers
 */
public class DNAMobs extends JavaPlugin {
    
    private static DNAMobs instance;
    private DNAManager dnaManager;
    private EvolutionManager evolutionManager;
    private DataStorage dataStorage;
    private boolean systemEnabled;
    
    @Override
    public void onEnable() {
        instance = this;
        
        // Save default config
        saveDefaultConfig();
        
        // Initialize storage
        initializeStorage();
        
        // Initialize managers
        dnaManager = new DNAManager(this);
        evolutionManager = new EvolutionManager(this);
        
        // Register listeners
        registerListeners();
        
        // Register commands
        registerCommands();
        
        // Start auto-save task
        startAutoSave();
        
        // Load data
        dataStorage.loadData();
        
        systemEnabled = getConfig().getBoolean("general.enabled", true);
        
        getLogger().info("DNAMobs v" + getDescription().getVersion() + " has been enabled!");
        getLogger().info("Author: MathTeacher");
        getLogger().info("Evolution system: " + (systemEnabled ? "ACTIVE" : "DISABLED"));
    }
    
    @Override
    public void onDisable() {
        // Save all data
        if (dataStorage != null) {
            dataStorage.saveData();
        }
        
        getLogger().info("DNAMobs has been disabled!");
    }
    
    /**
     * Initialize data storage based on config
     */
    private void initializeStorage() {
        String storageType = getConfig().getString("storage.type", "YAML");
        
        switch (storageType.toUpperCase()) {
            case "YAML":
                dataStorage = new YAMLStorage(this);
                break;
            // Future: Add MySQL/SQLite support
            default:
                dataStorage = new YAMLStorage(this);
                getLogger().warning("Unknown storage type, defaulting to YAML");
        }
    }
    
    /**
     * Register event listeners
     */
    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new MobCombatListener(this), this);
        getServer().getPluginManager().registerEvents(new MobSpawnListener(this), this);
    }
    
    /**
     * Register commands
     */
    private void registerCommands() {
        getCommand("dna").setExecutor(new DNACommand(this));
    }
    
    /**
     * Start auto-save task
     */
    private void startAutoSave() {
        long interval = getConfig().getLong("general.save-interval", 6000);
        
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            if (dataStorage != null) {
                dataStorage.saveData();
                if (getConfig().getBoolean("general.debug", false)) {
                    getLogger().info("Auto-saved DNA data");
                }
            }
        }, interval, interval);
    }
    
    /**
     * Reload plugin configuration and data
     */
    public void reload() {
        reloadConfig();
        systemEnabled = getConfig().getBoolean("general.enabled", true);
        dnaManager.reload();
        evolutionManager.reload();
    }
    
    // Getters
    public static DNAMobs getInstance() {
        return instance;
    }
    
    public DNAManager getDNAManager() {
        return dnaManager;
    }
    
    public EvolutionManager getEvolutionManager() {
        return evolutionManager;
    }
    
    public DataStorage getDataStorage() {
        return dataStorage;
    }
    
    public boolean isSystemEnabled() {
        return systemEnabled;
    }
    
    public void setSystemEnabled(boolean enabled) {
        this.systemEnabled = enabled;
        getConfig().set("general.enabled", enabled);
        saveConfig();
    }
}
