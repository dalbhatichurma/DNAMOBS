package com.dnamobs.managers;

import com.dnamobs.DNAMobs;
import com.dnamobs.models.DNAProfile;
import com.dnamobs.models.DamageType;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages DNA profiles for all mob types
 */
public class DNAManager {
    
    private final DNAMobs plugin;
    private final Map<EntityType, DNAProfile> globalProfiles;
    private final int killsPerTier;
    
    public DNAManager(DNAMobs plugin) {
        this.plugin = plugin;
        this.globalProfiles = new ConcurrentHashMap<>();
        this.killsPerTier = plugin.getConfig().getInt("evolution.kills-per-tier", 50);
        
        // Initialize profiles for trackable mobs
        initializeProfiles();
    }
    
    /**
     * Initialize DNA profiles for all configured mobs
     */
    private void initializeProfiles() {
        for (String mobKey : plugin.getConfig().getConfigurationSection("mobs").getKeys(false)) {
            try {
                EntityType type = EntityType.valueOf(mobKey.toUpperCase());
                if (plugin.getConfig().getBoolean("mobs." + mobKey + ".enabled", true)) {
                    globalProfiles.putIfAbsent(type, new DNAProfile(type));
                }
            } catch (IllegalArgumentException e) {
                plugin.getLogger().warning("Invalid mob type in config: " + mobKey);
            }
        }
    }
    
    /**
     * Record a mob kill with damage type
     */
    public void recordKill(EntityType mobType, DamageType damageType) {
        if (!plugin.isSystemEnabled()) return;
        
        DNAProfile profile = globalProfiles.computeIfAbsent(mobType, DNAProfile::new);
        profile.recordKill(damageType);
        
        // Check for evolution
        if (profile.shouldEvolve(killsPerTier)) {
            profile.evolve();
            if (plugin.getConfig().getBoolean("general.debug", false)) {
                plugin.getLogger().info(mobType.name() + " evolved to tier " + profile.getCurrentTier());
            }
        }
    }
    
    /**
     * Get DNA profile for mob type
     */
    public DNAProfile getProfile(EntityType mobType) {
        return globalProfiles.computeIfAbsent(mobType, DNAProfile::new);
    }
    
    /**
     * Get all DNA profiles
     */
    public Map<EntityType, DNAProfile> getAllProfiles() {
        return new HashMap<>(globalProfiles);
    }
    
    /**
     * Reset all DNA data
     */
    public void resetAll() {
        globalProfiles.values().forEach(DNAProfile::reset);
    }
    
    /**
     * Reset specific mob type
     */
    public void reset(EntityType mobType) {
        DNAProfile profile = globalProfiles.get(mobType);
        if (profile != null) {
            profile.reset();
        }
    }
    
    /**
     * Reload manager
     */
    public void reload() {
        // Profiles are kept, just reload if needed
    }
    
    /**
     * Load profiles from storage
     */
    public void loadProfiles(Map<EntityType, DNAProfile> profiles) {
        globalProfiles.clear();
        globalProfiles.putAll(profiles);
    }
}
