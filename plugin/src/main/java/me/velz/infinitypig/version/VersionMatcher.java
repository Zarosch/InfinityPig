package me.velz.infinitypig.version;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import me.velz.infinitypig.versions.Version;
import org.bukkit.Bukkit;

public class VersionMatcher {

    @Getter
    private final String serverVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);

    @Getter
    private final List<Class<? extends Version>> versions = Arrays.asList(
            Version_1_8_R3.class,
            Version_1_12_R1.class
    );
    
    public Version match() {
        try {
            return versions.stream()
                    .filter(version -> version.getSimpleName().substring(8).equals(serverVersion))
                    .findFirst().orElseThrow(() -> new RuntimeException("Your server version isn't supported in InfinityPig! (" + serverVersion +")"))
                    .newInstance();
        } catch (IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex); 
        }
    }
    
}
