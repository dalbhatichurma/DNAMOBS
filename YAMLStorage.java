package com.dnamobs.storage;

import com.dnamobs.DNAMobs;
import com.dnamobs.models.DNAProfile;
import com.dnamobs.models.DamageType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * YAML-based data storage implementation
 */
public class YAMLStorage implements DataStorage {
    
    private final DNAMobs plugin;
    private final File dataFile;
    private FileConfiguration dataConfig;
    
    public YAMLStorage(DNAMobs plugin) {
        this.plugin = plugin;
        this.dataFile = new File(plugin.getDataFolder(), "data.yml");
        
        if (!dataFile.exists()) {
            try {
                dataFile.getParentFile().mkdirs();
                dataFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Failed to create data file: " + e.getMessage());
            }
        }
        
        this.dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }
    
    @Override
    public void loadData() {
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        
        Map<EntityType, DNAProfile> profiles = new HashMap<>();
        
        ConfigurationSection mobsSection = dataConfig.getConfigurationSection("mobs");
        if (mobsSection == null) return;
        
        for (String mobKey : mobsSection.getKeys(false)) {
            try {
                EntityType type = EntityType.valueOf(mobKey);
                DNAProfile profile = new DNAProfile(type);
                
                String path = "mobs." + mobKey;
                profile.setTotalKills(dataConfig.getInt(path + ".total-kills", 0));
                profile.setCurrentTier(dataConfig.getInt(path + ".tier", 1));
                
                // Load damage counts
                ConfigurationSection damageSection = dataConfig.getConfigurationSection(path + ".damage");
                if (damageSection != null) {
                    for (String damageKey : damageSection.getKeys(false)) {
                        try {
                            DamageType damageType = DamageType.valueOf(damageKey);
                            int count = damageSection.getInt(damageKey, 0);
                            profile.setDamageCount(damageType, count);
                        } catch (IllegalArgumentException e) {
                            plugin.getLogger().warning("Invalid damage type: " + damageKey);
                        }
                    }
                }
                
                profiles.put(type, profile);
            } catch (IllegalArgumentException e) {
                plugin.getLogger().warning("Invalid mob type in data: " + mobKey);
            }
        }
        
        plugin.getDNAManager().loadProfiles(profiles);
        plugin.getLogger().info("Loaded DNA data for " + profiles.size() + " mob types");
    }
    
    @Override
    public void saveData() {
        Map<EntityType, DNAProfile> profiles = plugin.getDNAManager().getAllProfiles();
        
        // Clear existing data
        dataConfig.set("mobs", null);
        
        // Save profiles
        for (Map.Entry<EntityType, DNAProfile> entry : profiles.entrySet()) {
            EntityType type = entry.getKey();
            DNAProfile profile = entry.getValue();
            
            String path = "mobs." + type.name();
            dataConfig.set(path + ".total-kills", profile.getTotalKills());
            dataConfig.set(path + ".tier", profile.getCurrentTier());
            
            // Save damage counts
            for (Map.Entry<DamageType, Integer> damageEntry : profile.getDamageCount().entrySet()) {
                dataConfig.set(path + ".damage." + damageEntry.getKey().name(), damageEntry.getValue());
            }
        }
        
        // Save to file
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save data: " + e.getMessage());
        }
    }
    
    @Override
    public void clearData() {
        dataConfig.set("mobs", null);
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to clear data: " + e.getMessage());
        }
    }
}
