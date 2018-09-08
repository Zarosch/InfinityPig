package me.velz.infinitypig;

import java.util.HashMap;
import java.util.Random;
import lombok.Getter;
import me.velz.infinitypig.commands.InfinityPigCommand;
import me.velz.infinitypig.listeners.EntityDeathListener;
import me.velz.infinitypig.listeners.VehicleEnterListener;
import me.velz.infinitypig.objects.InfinityPigEntity;
import me.velz.infinitypig.utils.FileManager;
import me.velz.infinitypig.utils.MessageUtil;
import me.velz.infinitypig.version.VersionMatcher;
import me.velz.infinitypig.versions.Version;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
    
    private final VersionMatcher versionMatcher = new VersionMatcher();
    
    @Getter
    private Version version;
    
    @Override
    public void onEnable() {
        InfinityPig.plugin = this;
        this.version = versionMatcher.match();
        this.getFileManager().setDefaults();
        this.getFileManager().loadConfig();
        MessageUtil.load();
        Bukkit.getPluginManager().registerEvents(new EntityDeathListener(this), this);
        Bukkit.getPluginManager().registerEvents(new VehicleEnterListener(this), this);
        getCommand("infinitypig").setExecutor(new InfinityPigCommand(this));
    }

    @Override
    public void onDisable() {
        removeAllEntities();
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
        this.version.disableAI(entity);
    }

}
