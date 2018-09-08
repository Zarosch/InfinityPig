package me.velz.infinitypig.listeners;

import me.velz.infinitypig.InfinityPig;
import org.bukkit.Effect;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathListener implements Listener {
    
    private final InfinityPig plugin;

    public EntityDeathListener(InfinityPig plugin) {
        this.plugin = plugin;
    } 
    
    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        plugin.getEntities().values().stream().filter((entity) -> (entity.getEntityId() == event.getEntity().getEntityId())).forEachOrdered((entity) -> {
            if(!plugin.getFileManager().isDropExp()) {
                event.setDroppedExp(0);
            }
            entity.spawn();
            entity.getEntityLocation().getWorld().playEffect(entity.getEntityLocation(), Effect.MOBSPAWNER_FLAMES, 20);
        });
    }
    
}
