package me.velz.infinitypig.objects;

import lombok.Getter;
import lombok.Setter;
import me.velz.infinitypig.InfinityPig;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class InfinityPigEntity {
    
    @Getter @Setter
    private Integer entityId = 0;
    
    @Getter @Setter
    private EntityType entityType;
    
    @Getter @Setter
    private Location entityLocation;
    
    @Getter @Setter
    private Entity entity;

    public InfinityPigEntity(EntityType entityType, Location entityLocation) {
        this.entityType = entityType;
        this.entityLocation = entityLocation;
    }
    
    public void remove() {
        if(!entityLocation.getChunk().isLoaded()) {
            entityLocation.getWorld().loadChunk(entityLocation.getChunk());
        }
        entity.remove();
        entityId = 0;
    }
    
    public void spawn() {
        if(!entityLocation.getChunk().isLoaded()) {
            entityLocation.getWorld().loadChunk(entityLocation.getChunk());
        }
        setEntity(InfinityPig.getPlugin().spawnEntity(entityType, entityLocation));
        setEntityId(getEntity().getEntityId());
    }
    
    public void setType(EntityType entityType) {
        remove();
        this.entityType = entityType;
        spawn();
    }
    
    public void setLocation(Location location) {
        remove();
        this.entityLocation = location;
        spawn();
    } 
    
}
