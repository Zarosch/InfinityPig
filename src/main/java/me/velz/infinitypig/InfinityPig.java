package me.velz.infinitypig;

import java.util.HashMap;
import java.util.Random;
import lombok.Getter;
import me.velz.infinitypig.commands.InfinityPigCommand;
import me.velz.infinitypig.listeners.EntityDeathListener;
import me.velz.infinitypig.objects.InfinityPigEntity;
import me.velz.infinitypig.utils.FileManager;
import me.velz.infinitypig.utils.MessageUtil;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

public class InfinityPig extends JavaPlugin {

    @Getter
    public static InfinityPig plugin;
    
    @Getter
    private final HashMap<String, InfinityPigEntity> entities = new HashMap<>();

    @Getter
    private final FileManager fileManager = new FileManager(this);
    
    @Override
    public void onEnable() {
        InfinityPig.plugin = this;
        this.getFileManager().setDefaults();
        this.getFileManager().loadConfig();
        MessageUtil.load();
        Bukkit.getPluginManager().registerEvents(new EntityDeathListener(this), this);
        getCommand("infinitypig").setExecutor(new InfinityPigCommand(this));
    }


    public void removeAllEntities() {
        entities.values().forEach((entity) -> {
            entity.remove();
        });
    }
    
    public void spawnAllEntities() {
        entities.values().forEach((entity) -> {
            entity.spawn();
        });
    }

    public Entity spawnEntity(EntityType type, Location loc) {
        Entity entity = loc.getWorld().spawnEntity(loc, type);
        entity.setCustomNameVisible(true);
        Random random = new Random();
        entity.setCustomName(fileManager.getNames().get(random.nextInt(fileManager.getNames().size())));
        disableAI(entity);
        return entity;
    }

    private void disableAI(Entity entity) {
        net.minecraft.server.v1_8_R3.Entity nmsEnt = ((CraftEntity) entity).getHandle();
        NBTTagCompound tag = nmsEnt.getNBTTag();

        if (tag == null) {
            tag = new NBTTagCompound();
        }

        nmsEnt.c(tag);
        tag.setInt("NoAI", 1);
        nmsEnt.f(tag);
    }

}
