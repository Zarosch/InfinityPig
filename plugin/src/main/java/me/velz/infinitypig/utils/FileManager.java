package me.velz.infinitypig.utils;

import java.util.ArrayList;
import lombok.Getter;
import me.velz.infinitypig.InfinityPig;
import me.velz.infinitypig.objects.InfinityPigEntity;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;

public class FileManager {

    private final InfinityPig plugin;

    public FileManager(InfinityPig plugin) {
        this.plugin = plugin;
    }

    @Getter
    private String language;

    @Getter
    private ArrayList<String> names;

    @Getter
    private final FileBuilder config = new FileBuilder("plugins/InfinityPig/", "config.yml");

    public void loadConfig() {
        this.getConfig().load();
        this.names = new ArrayList<>();
        this.language = this.getConfig().getString("language");
        this.getConfig().getStringList("infinitypig.names").forEach((name) -> {
            this.getNames().add(ChatColor.translateAlternateColorCodes('&', name));
        });
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                if (config.getConfiguration().contains("entities")) {
                    config.getConfiguration().getConfigurationSection("entities").getKeys(false).forEach((id) -> {
                        try {
                            plugin.getEntities().put(id, new InfinityPigEntity(EntityType.valueOf(config.getString("entities." + id + ".type")), config.getLocation("entities." + id + ".location")));
                            plugin.getEntities().get(id).spawn();
                        } catch (NullPointerException ex) {
                            System.out.println("[InfinityPig] Entity " + id + " could not be loaded.");
                        }
                    });
                }
            }
        }, 200L);
    }

    public void setDefaults() {
        this.getConfig().addDefault("language", "en");
        this.getConfig().addDefault("infinitypig.names", new String[]{
            "&dHilfe, ich bin ein armes Schwein",
            "&dHalt! Bitte schlag mich nicht.",
            "&dSchwein gehabt!",
            "&dArmes Schwein. Oink.",
            "&dLeckeres zartes Schwein"
        });
        this.getConfig().save();
    }

}
