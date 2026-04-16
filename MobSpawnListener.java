package com.dnamobs.listeners;

import com.dnamobs.DNAMobs;
import com.dnamobs.utils.ColorUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

/**
 * Listens to mob spawn events to apply evolution
 */
public class MobSpawnListener implements Listener {
    
    private final DNAMobs plugin;
    
    public MobSpawnListener(DNAMobs plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Apply evolution to newly spawned mobs
     */
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onMobSpawn(CreatureSpawnEvent event) {
        if (!plugin.isSystemEnabled()) return;
        if (!(event.getEntity() instanceof Monster)) return;
        
        // Skip spawner and custom spawns to avoid exploitation
        CreatureSpawnEvent.SpawnReason reason = event.getSpawnReason();
        if (reason == CreatureSpawnEvent.SpawnReason.SPAWNER || 
            reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG ||
            reason == CreatureSpawnEvent.SpawnReason.CUSTOM) {
            return;
        }
        
        LivingEntity entity = event.getEntity();
        
        // Apply evolution with slight delay to ensure entity is fully loaded
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            if (entity.isValid() && !entity.isDead()) {
                plugin.getEvolutionManager().applyEvolution(entity);
            }
        }, 1L);
    }
}
