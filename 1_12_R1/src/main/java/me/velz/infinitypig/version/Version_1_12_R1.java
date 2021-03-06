package me.velz.infinitypig.version;

import me.velz.infinitypig.versions.Version;
import org.bukkit.entity.Entity;

public class Version_1_12_R1 implements Version {

    @Override
    public void disableAI(Entity entity) {
        entity.getWorld().getLivingEntities().stream().filter((livingEntity) -> (livingEntity.getEntityId() == entity.getEntityId())).forEachOrdered((livingEntity) -> {
            livingEntity.setAI(false);
        });
    }
    
}
