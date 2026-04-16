package com.dnamobs.models;

import org.bukkit.entity.LivingEntity;

import java.util.UUID;

/**
 * Represents an individual evolved mob instance
 */
public class EvolvedMob {
    
    private final UUID entityId;
    private final LivingEntity entity;
    private final int tier;
    private final boolean isElite;
    private final DamageType resistantTo;
    
    public EvolvedMob(LivingEntity entity, int tier, boolean isElite, DamageType resistantTo) {
        this.entityId = entity.getUniqueId();
        this.entity = entity;
        this.tier = tier;
        this.isElite = isElite;
        this.resistantTo = resistantTo;
    }
    
    public UUID getEntityId() {
        return entityId;
    }
    
    public LivingEntity getEntity() {
        return entity;
    }
    
    public int getTier() {
        return tier;
    }
    
    public boolean isElite() {
        return isElite;
    }
    
    public DamageType getResistantTo() {
        return resistantTo;
    }
}
