package me.velz.infinitypig.utils;

import lombok.Getter;

public enum MessageUtil {
    
    PREFIX("§f[§dInfinityPig§f] "),
    // Error Messages
    ERROR_NOPERMISSIONS("§cDazu hast du keine Berechtigung."),
    ERROR_PLAYERONLY("§cDieser Befehl kann nur von einem Spieler genutzt werden."),
    ERROR_PLAYERNOTFOUND("§cDieser Spieler konnte nicht gefunden werden."),
    ERROR_NONUMBER("§cDu musst eine gültige Zahl angeben."),
    ERROR_NOITEMINHAND("§cDu hast kein Item in der Hand."),
    ERROR_SYNTAX("§cSyntax Fehler, bitte nutze: %command"),
    ERROR_ENTITYTYPENOTFOUND("§cDieser Entity Type konnte nicht gefunden werden."),
    ERROR_NAMEISUSED("§cDiesen namen gibt es bereits."),
    ERROR_NAMENOTFOUND("§cEs konnte kein Entity mit diesem namen gefunden werden."),
    
    COMMAND_HELP_HEAD("§6InfinityPig"),
    COMMAND_HELP_RELOAD("§6/ipig reload §7Konfiguration neuladen"),
    COMMAND_HELP_ADD("§6/ipig add [name] [type] §7Entity erstellen"),
    COMMAND_HELP_REMOVE("§6/ipig remove [name] [type] §7Entity löschen"),
    COMMAND_HELP_LIST("§6/ipig list §7Entities auflisten"),
    COMMAND_ENTITYSPAWNED("§aEntity §e%name §awurde erstellt."),
    COMMAND_ENTITYREMOVED("§aEntity §e%name §awurde entfernt."),
    COMMAND_LISTENITIES("§aAlle Entities: §e%entities"),
    COMMAND_NOENTITYFOUND("§cEs konnte kein Entity gefunden werden."),
    COMMAND_RELOAD("§aKonfiguration wurde neugeladen.");

    @Getter
    private String local;

    private MessageUtil(String local) {
        this.local = local;
    }

    public static void load() {
        FileBuilder message = new FileBuilder("plugins/InfinityPig", "messages.yml");
        for (MessageUtil m : MessageUtil.values()) {
            message.addDefault("message." + m.toString().replaceAll("_", ".").toLowerCase(), m.local.replaceAll("§", "&"));
        }
        message.save();
        for (MessageUtil m : MessageUtil.values()) {
            m.local = message.getConfiguration().getString("message." + m.toString().replaceAll("_", ".").toLowerCase()).replaceAll("&", "§");
        }
    }

    public static void save(MessageUtil m) {
        FileBuilder message = new FileBuilder("plugins/InfinityPig", "messages.yml");
        message.save();
    }
    
}
