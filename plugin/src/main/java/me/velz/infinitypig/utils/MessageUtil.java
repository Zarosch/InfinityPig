package me.velz.infinitypig.utils;

import lombok.Getter;
import me.velz.infinitypig.InfinityPig;

public enum MessageUtil {

    PREFIX("", "§f[§dInfinityPig§f] ", "§f[§dInfinityPig§f] "),
    // Error Messages
    ERROR_NOPERMISSIONS("", "§cDazu hast du keine Berechtigung.", "§cYou dont have permissions."),
    ERROR_PLAYERONLY("", "§cDieser Befehl kann nur von einem Spieler genutzt werden.", "§cThis command can only be used by a player."),
    ERROR_PLAYERNOTFOUND("", "§cDieser Spieler konnte nicht gefunden werden.", "§cThis player could not be found."),
    ERROR_SYNTAX("", "§cSyntax Fehler, bitte nutze: %command", "§cSyntax Error, please use: %command"),
    ERROR_ENTITYTYPENOTFOUND("", "§cDieser Entity Type konnte nicht gefunden werden.", "§cThis entity type could not be found."),
    ERROR_NAMEISUSED("", "§cDiesen namen gibt es bereits.", "§cThis name already exists."),
    ERROR_NAMENOTFOUND("", "§cEs konnte kein Entity mit diesem namen gefunden werden.", "§cNo enity with this name could be found."),
    COMMAND_HELP_HEAD("", "§6InfinityPig", "§6InfinityPig"),
    COMMAND_HELP_RELOAD("", "§6/ipig reload §7Konfiguration neuladen", "§6/ipig reload §7Reload Configuration"),
    COMMAND_HELP_ADD("", "§6/ipig add [name] [type] §7Entity erstellen", "§6/ipig add [name] [type] §7Create Entity"),
    COMMAND_HELP_REMOVE("", "§6/ipig remove [name] [type] §7Entity löschen", "§6/ipig remove [name] [type] §7Delete Entity"),
    COMMAND_HELP_LIST("", "§6/ipig list §7Entities auflisten", "§6/ipig list §7List all Entities"),
    COMMAND_ENTITYSPAWNED("", "§aEntity §e%name §awurde erstellt.", "§aEntity §e%name §acreated."),
    COMMAND_ENTITYREMOVED("", "§aEntity §e%name §awurde entfernt.", "§aEntity §e%name §aremoved."),
    COMMAND_LISTENITIES("", "§aAlle Entities: §e%entities", "§aAll Entities: §e%entities"),
    COMMAND_NOENTITYFOUND("", "§cEs konnte kein Entity gefunden werden.", "§cNo entity could be found."),
    COMMAND_RELOAD("", "§aKonfiguration wurde neugeladen.", "§aConfiguration has been reloaded.");

    @Getter
    private String local, german, english;

    private MessageUtil(String local, String german, String english) {
        this.local = local;
        this.german = german;
        this.english = english;
    }

    public static void load() {
        FileBuilder message_en = new FileBuilder("plugins/InfinityPig", "messages_en.yml");
        for (MessageUtil m : MessageUtil.values()) {
            message_en.addDefault("message." + m.toString().replaceAll("_", ".").toLowerCase(), m.english.replaceAll("§", "&"));
        }
        message_en.save();
        FileBuilder message_de = new FileBuilder("plugins/InfinityPig", "messages_de.yml");
        for (MessageUtil m : MessageUtil.values()) {
            message_de.addDefault("message." + m.toString().replaceAll("_", ".").toLowerCase(), m.german.replaceAll("§", "&"));
        }
        message_de.save();
        FileBuilder message = new FileBuilder("plugins/InfinityPig", "messages_" + InfinityPig.getPlugin().getFileManager().getLanguage() + ".yml");
        if(!message.getFile().exists()) {
            message = new FileBuilder("plugins/InfinityPig", "messages_en.yml");
        }
        for (MessageUtil m : MessageUtil.values()) {
            m.local = message.getConfiguration().getString("message." + m.toString().replaceAll("_", ".").toLowerCase()).replaceAll("&", "§");
        }
    }

}
