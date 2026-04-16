package com.dnamobs.models;

import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the DNA profile of a mob type
 * Tracks damage types and evolution progress
 */
public class DNAProfile {
    
    private final EntityType mobType;
    private final Map<DamageType, Integer> damageCount;
    private int totalKills;
    private int currentTier;
    private long lastUpdate;
    
    public DNAProfile(EntityType mobType) {
        this.mobType = mobType;
        this.damageCount = new HashMap<>();
        this.totalKills = 0;
        this.currentTier = 1;
        this.lastUpdate = System.currentTimeMillis();
        
        // Initialize damage types
        for (DamageType type : DamageType.values()) {
            damageCount.put(type, 0);
        }
    }
    
    /**
     * Record a kill with specific damage type
     */
    public void recordKill(DamageType damageType) {
        damageCount.put(damageType, damageCount.getOrDefault(damageType, 0) + 1);
        totalKills++;
        lastUpdate = System.currentTimeMillis();
    }
    
    /**
     * Get the most common damage type
     */
    public DamageType getDominantDamageType() {
        return damageCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(DamageType.MELEE);
    }
    
    /**
     * Get percentage of specific damage type
     */
    public double getDamageTypePercentage(DamageType type) {
        if (totalKills == 0) return 0.0;
        return (damageCount.getOrDefault(type, 0) * 100.0) / totalKills;
    }
    
    /**
     * Check if mob should evolve to next tier
     */
    public boolean shouldEvolve(int killsPerTier) {
        return totalKills >= (currentTier * killsPerTier) && currentTier < 4;
    }
    
    /**
     * Evolve to next tier
     */
    public void evolve() {
        if (currentTier < 4) {
            currentTier++;
        }
    }
    
    /**
     * Reset DNA profile
     */
    public void reset() {
        damageCount.clear();
        for (DamageType type : DamageType.values()) {
            damageCount.put(type, 0);
        }
        totalKills = 0;
        currentTier = 1;
    }
    
    // Getters and Setters
    public EntityType getMobType() {
        return mobType;
    }
    
    public Map<DamageType, Integer> getDamageCount() {
        return new HashMap<>(damageCount);
    }
    
    public int getTotalKills() {
        return totalKills;
    }
    
    public void setTotalKills(int kills) {
        this.totalKills = kills;
    }
    
    public int getCurrentTier() {
        return currentTier;
    }
    
    public void setCurrentTier(int tier) {
        this.currentTier = Math.min(Math.max(tier, 1), 4);
    }
    
    public long getLastUpdate() {
        return lastUpdate;
    }
    
    public void setDamageCount(DamageType type, int count) {
        damageCount.put(type, count);
    }
}
