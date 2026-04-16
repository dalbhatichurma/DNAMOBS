package com.dnamobs.listeners;

import com.dnamobs.DNAMobs;
import com.dnamobs.models.DamageType;
import com.dnamobs.models.EvolvedMob;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.projectiles.ProjectileSource;

/**
 * Listens to mob combat events to track DNA and apply resistances
 */
public class MobCombatListener implements Listener {
    
    private final DNAMobs plugin;
    
    public MobCombatListener(DNAMobs plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Track mob deaths and record DNA data
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMobDeath(EntityDeathEvent event) {
        if (!plugin.isSystemEnabled()) return;
        
        LivingEntity entity = event.getEntity();
        if (!(entity instanceof Monster) && !(entity instanceof Flying)) return;
        
        // Get damage cause
        EntityDamageEvent lastDamage = entity.getLastDamageCause();
        if (lastDamage == null) return;
        
        DamageType damageType = getDamageType(lastDamage);
        
        // Record kill in DNA system
        plugin.getDNAManager().recordKill(entity.getType(), damageType);
        
        // Remove from evolved mobs tracking
        plugin.getEvolutionManager().removeEvolvedMob(entity.getUniqueId());
        
        // Elite mob rewards
        EvolvedMob evolvedMob = plugin.getEvolutionManager().getEvolvedMob(entity.getUniqueId());
        if (evolvedMob != null && evolvedMob.isElite()) {
            applyEliteRewards(event);
        }
    }
    
    /**
     * Apply damage resistance for evolved mobs
     */
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onMobDamage(EntityDamageByEntityEvent event) {
        if (!plugin.isSystemEnabled()) return;
        if (!(event.getEntity() instanceof LivingEntity)) return;
        
        LivingEntity entity = (LivingEntity) event.getEntity();
        EvolvedMob evolvedMob = plugin.getEvolutionManager().getEvolvedMob(entity.getUniqueId());
        
        if (evolvedMob == null) return;
        
        // Determine damage type
        DamageType damageType = getDamageType(event);
        
        // Apply resistance
        double reduction = plugin.getEvolutionManager().calculateDamageReduction(evolvedMob, damageType);
        if (reduction > 0) {
            double newDamage = event.getDamage() * (1.0 - reduction);
            event.setDamage(newDamage);
            
            if (plugin.getConfig().getBoolean("general.debug", false)) {
                plugin.getLogger().info("Reduced " + damageType + " damage by " + (reduction * 100) + "%");
            }
        }
    }
    
    /**
     * Determine damage type from event
     */
    private DamageType getDamageType(EntityDamageEvent event) {
        if (event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent entityEvent = (EntityDamageByEntityEvent) event;
            Entity damager = entityEvent.getDamager();
            
            // Projectile damage
            if (damager instanceof Projectile) {
                Projectile projectile = (Projectile) damager;
                ProjectileSource shooter = projectile.getShooter();
                
                if (shooter instanceof Player) {
                    if (projectile instanceof Arrow || projectile instanceof SpectralArrow) {
                        return DamageType.RANGED;
                    }
                }
            }
            
            // Melee damage
            if (damager instanceof Player) {
                return DamageType.MELEE;
            }
        }
        
        // Environmental damage
        EntityDamageEvent.DamageCause cause = event.getCause();
        switch (cause) {
            case FIRE:
            case FIRE_TICK:
            case LAVA:
            case HOT_FLOOR:
                return DamageType.FIRE;
            case BLOCK_EXPLOSION:
            case ENTITY_EXPLOSION:
                return DamageType.EXPLOSION;
            case MAGIC:
            case POISON:
            case WITHER:
                return DamageType.MAGIC;
            case FALL:
            case DROWNING:
            case SUFFOCATION:
            case VOID:
                return DamageType.ENVIRONMENTAL;
            default:
                return DamageType.UNKNOWN;
        }
    }
    
    /**
     * Apply elite mob rewards
     */
    private void applyEliteRewards(EntityDeathEvent event) {
        if (!plugin.getConfig().getBoolean("elite.rewards.rare-drops", true)) return;
        
        // Multiply experience
        double expMult = plugin.getConfig().getDouble("elite.rewards.exp-multiplier", 3.0);
        event.setDroppedExp((int) (event.getDroppedExp() * expMult));
        
        // Additional drops handled by Minecraft's natural loot system
    }
}
