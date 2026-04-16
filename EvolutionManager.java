package com.dnamobs.managers;

import com.dnamobs.DNAMobs;
import com.dnamobs.models.DNAProfile;
import com.dnamobs.models.DamageType;
import com.dnamobs.models.EvolvedMob;
import com.dnamobs.utils.ColorUtil;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages mob evolution, abilities, and elite variants
 */
public class EvolutionManager {
    
    private final DNAMobs plugin;
    private final Map<UUID, EvolvedMob> evolvedMobs;
    private final Random random;
    
    public EvolutionManager(DNAMobs plugin) {
        this.plugin = plugin;
        this.evolvedMobs = new ConcurrentHashMap<>();
        this.random = new Random();
    }
    
    /**
     * Apply evolution to a spawned mob
     */
    public void applyEvolution(LivingEntity entity) {
        if (!plugin.isSystemEnabled()) return;
        if (!isEvolveableMob(entity.getType())) return;
        
        DNAProfile profile = plugin.getDNAManager().getProfile(entity.getType());
        int tier = profile.getCurrentTier();
        
        if (tier <= 1) return;
        
        // Check for elite mutation
        boolean isElite = random.nextDouble() < plugin.getConfig().getDouble("evolution.elite-mutation-chance", 0.05);
        
        // Get dominant damage type for resistance
        DamageType resistantTo = profile.getDominantDamageType();
        
        // Create evolved mob
        EvolvedMob evolvedMob = new EvolvedMob(entity, tier, isElite, resistantTo);
        evolvedMobs.put(entity.getUniqueId(), evolvedMob);
        
        // Apply tier bonuses
        applyTierBonuses(entity, tier);
        
        // Apply elite bonuses
        if (isElite) {
            applyEliteBonuses(entity);
        }
        
        // Apply mob-specific abilities
        applyMobAbilities(entity, tier);
    }
    
    /**
     * Apply tier-based stat bonuses
     */
    private void applyTierBonuses(LivingEntity entity, int tier) {
        String mobKey = entity.getType().name().toLowerCase();
        String tierPath = "mobs." + mobKey + ".tier-" + tier;
        
        // Health boost
        double healthBoost = plugin.getConfig().getDouble(tierPath + ".health-boost", 0);
        if (healthBoost > 0) {
            double maxHealth = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
            entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth + healthBoost);
            entity.setHealth(maxHealth + healthBoost);
        }
        
        // Speed boost
        double speedBoost = plugin.getConfig().getDouble(tierPath + ".speed-boost", 0);
        if (speedBoost > 0) {
            double speed = entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speed + speedBoost);
        }
        
        // Damage boost
        double damageBoost = plugin.getConfig().getDouble(tierPath + ".damage-boost", 0);
        if (damageBoost > 0 && entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE) != null) {
            double damage = entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue();
            entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(damage + damageBoost);
        }
    }
    
    /**
     * Apply elite mob bonuses
     */
    private void applyEliteBonuses(LivingEntity entity) {
        if (!plugin.getConfig().getBoolean("elite.enabled", true)) return;
        
        // Custom name
        String nameFormat = plugin.getConfig().getString("elite.name-format", "&c&l[ELITE] &6%mob%");
        String mobName = entity.getType().name().replace("_", " ");
        String displayName = ColorUtil.colorize(nameFormat.replace("%mob%", mobName));
        entity.setCustomName(displayName);
        entity.setCustomNameVisible(true);
        
        // Stat multipliers
        double healthMult = plugin.getConfig().getDouble("elite.health-multiplier", 2.0);
        double maxHealth = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth * healthMult);
        entity.setHealth(maxHealth * healthMult);
        
        double speedMult = plugin.getConfig().getDouble("elite.speed-multiplier", 1.3);
        double speed = entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
        entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speed * speedMult);
        
        // Potion effects
        if (plugin.getConfig().getBoolean("elite.abilities.regeneration", true)) {
            entity.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0, false, false));
        }
        if (plugin.getConfig().getBoolean("elite.abilities.resistance", true)) {
            entity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0, false, false));
        }
        if (plugin.getConfig().getBoolean("elite.abilities.fire-resistance", true)) {
            entity.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, false, false));
        }
        
        // Particles
        if (plugin.getConfig().getBoolean("elite.particles.enabled", true)) {
            startParticleEffect(entity);
        }
    }
    
    /**
     * Apply mob-specific abilities based on tier
     */
    private void applyMobAbilities(LivingEntity entity, int tier) {
        String mobKey = entity.getType().name().toLowerCase();
        String tierPath = "mobs." + mobKey + ".tier-" + tier;
        
        // Zombie abilities
        if (entity instanceof Zombie) {
            if (plugin.getConfig().getBoolean(tierPath + ".break-blocks", false)) {
                ((Zombie) entity).setCanBreakDoors(true);
            }
        }
        
        // Creeper abilities
        if (entity instanceof Creeper) {
            Creeper creeper = (Creeper) entity;
            double explosionPower = plugin.getConfig().getDouble(tierPath + ".explosion-power", 1.0);
            if (explosionPower > 1.0) {
                creeper.setExplosionRadius((int) (3 * explosionPower));
            }
        }
    }
    
    /**
     * Start particle effect for elite mobs
     */
    private void startParticleEffect(LivingEntity entity) {
        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            if (entity.isDead() || !entity.isValid()) return;
            
            String particleType = plugin.getConfig().getString("elite.particles.type", "FLAME");
            int count = plugin.getConfig().getInt("elite.particles.count", 3);
            
            try {
                Particle particle = Particle.valueOf(particleType);
                entity.getWorld().spawnParticle(particle, entity.getLocation().add(0, 1, 0), count, 0.3, 0.5, 0.3, 0);
            } catch (IllegalArgumentException e) {
                plugin.getLogger().warning("Invalid particle type: " + particleType);
            }
        }, 0L, 10L);
    }
    
    /**
     * Get evolved mob data
     */
    public EvolvedMob getEvolvedMob(UUID entityId) {
        return evolvedMobs.get(entityId);
    }
    
    /**
     * Remove evolved mob from tracking
     */
    public void removeEvolvedMob(UUID entityId) {
        evolvedMobs.remove(entityId);
    }
    
    /**
     * Check if mob type can evolve
     */
    private boolean isEvolveableMob(EntityType type) {
        String mobKey = type.name().toLowerCase();
        return plugin.getConfig().getBoolean("mobs." + mobKey + ".evolution-enabled", false);
    }
    
    /**
     * Calculate damage reduction based on resistance
     */
    public double calculateDamageReduction(EvolvedMob evolvedMob, DamageType damageType) {
        if (evolvedMob.getResistantTo() != damageType) return 0.0;
        
        int tier = evolvedMob.getTier();
        String resistanceKey = "resistance.tier-" + tier;
        return plugin.getConfig().getDouble(resistanceKey, 0) / 100.0;
    }
    
    /**
     * Reload manager
     */
    public void reload() {
        // Keep evolved mobs, just reload if needed
    }
}
