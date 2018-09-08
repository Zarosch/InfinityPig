package me.velz.infinitypig.commands;

import me.velz.infinitypig.InfinityPig;
import me.velz.infinitypig.objects.InfinityPigEntity;
import me.velz.infinitypig.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class InfinityPigCommand implements CommandExecutor {

    private final InfinityPig plugin;

    public InfinityPigCommand(InfinityPig plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {

        if (!cs.hasPermission("infinitypig.command")) {
            cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ERROR_NOPERMISSIONS.getLocal());
            return true;
        }

        if (args.length == 0) {
            cs.sendMessage(MessageUtil.COMMAND_HELP_HEAD.getLocal());
            cs.sendMessage(MessageUtil.COMMAND_HELP_RELOAD.getLocal());
            cs.sendMessage(MessageUtil.COMMAND_HELP_ADD.getLocal());
            cs.sendMessage(MessageUtil.COMMAND_HELP_REMOVE.getLocal());
            cs.sendMessage(MessageUtil.COMMAND_HELP_LIST.getLocal());
            return true;
        }

        if (args[0].equalsIgnoreCase("add")) {
            if (args.length != 3) {
                cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ERROR_SYNTAX.getLocal().replaceAll("%command", "/ipig add [Name] [Type]"));
                return true;
            }
            final Player player = (Player) cs;
            if (EntityType.valueOf(args[2]) == null) {
                cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ERROR_ENTITYTYPENOTFOUND.getLocal());
                return true;
            }
            if (plugin.getFileManager().getConfig().getConfiguration().contains("entities." + args[1])) {
                cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ERROR_NAMEISUSED.getLocal());
                return true;
            }
            EntityType type = EntityType.valueOf(args[2]);

            plugin.getFileManager().getConfig().set("entities." + args[1] + ".type", type.toString());
            plugin.getFileManager().getConfig().set("entities." + args[1] + ".location", player.getLocation());
            plugin.getFileManager().getConfig().save();
            plugin.getEntities().put(args[1], new InfinityPigEntity(type, player.getLocation()));
            plugin.getEntities().get(args[1]).spawn();
            cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.COMMAND_ENTITYSPAWNED.getLocal().replaceAll("%name", args[1]));
            return true;
        }

        if (args[0].equalsIgnoreCase("remove")) {
            if (args.length != 2) {
                cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ERROR_SYNTAX.getLocal().replaceAll("%command", "/ipig remove [Name]"));
                return true;
            }
            if (!plugin.getFileManager().getConfig().getConfiguration().contains("entities." + args[1])) {
                cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.ERROR_NAMENOTFOUND.getLocal());
                return true;
            }
            plugin.getFileManager().getConfig().getConfiguration().set("entities." + args[1], null);
            plugin.getFileManager().getConfig().save();
            plugin.getEntities().get(args[1]).remove();
            plugin.getEntities().remove(args[1]);
            cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.COMMAND_ENTITYREMOVED.getLocal().replaceAll("%name", args[1]));
            return true;
        }

        if (args[0].equalsIgnoreCase("list")) {
            String list = "";
            if (!plugin.getEntities().isEmpty()) {
                for (String id : plugin.getEntities().keySet()) {
                    list = list  + id + ", ";
                }
                list = list.substring(0, list.length() - 2);
            } else {
                list = MessageUtil.COMMAND_NOENTITYFOUND.getLocal();
            }
            cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.COMMAND_LISTENITIES.getLocal().replaceAll("%entities", list));
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            plugin.removeAllEntities();
            plugin.getEntities().clear();
            plugin.getFileManager().loadConfig();
            MessageUtil.load();
            cs.sendMessage(MessageUtil.PREFIX.getLocal() + MessageUtil.COMMAND_RELOAD.getLocal());
            return true;
        }

        return true;
    }

}
