package me.velz.infinitypig.listeners;

import me.velz.infinitypig.InfinityPig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;

public class VehicleEnterListener implements Listener {
    
    private final InfinityPig plugin;

    public VehicleEnterListener(InfinityPig plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onEnter(VehicleEnterEvent event) {
        plugin.getEntities().values().stream().filter((entity) -> (entity.getEntityId() == event.getEntered().getEntityId())).forEachOrdered((_item) -> {
            event.setCancelled(true);
        });
    }
    
}
