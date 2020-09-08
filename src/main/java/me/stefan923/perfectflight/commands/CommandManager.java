package me.stefan923.perfectflight.commands;

import me.stefan923.perfectflight.PerfectFlight;
import me.stefan923.perfectflight.commands.type.CommandFly;
import me.stefan923.perfectflight.commands.type.CommandPerfectFlight;
import me.stefan923.perfectflight.commands.type.CommandReload;
import me.stefan923.perfectflight.exceptions.MissingPermissionException;
import me.stefan923.perfectflight.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandManager implements CommandExecutor, MessageUtils {

    private static final List<AbstractCommand> commands = new ArrayList<>();
    private final PerfectFlight instance;

    public CommandManager(PerfectFlight instance) {
        this.instance = instance;
        TabManager tabManager = new TabManager(this);

        FileConfiguration settings = instance.getSettingsManager().getConfig();

        instance.getCommand("perfectflight").setExecutor(this);
        AbstractCommand commandPerfectFlight = addCommand(new CommandPerfectFlight());
        addCommand(new CommandReload(commandPerfectFlight));

        if (settings.getBoolean("Enabled Commands.Fly")) {
            instance.getCommand("fly").setExecutor(this);
            addCommand(new CommandFly());
        }

        for (AbstractCommand abstractCommand : commands) {
            if (abstractCommand.getParent() != null) continue;
            instance.getCommand(abstractCommand.getCommand()).setTabCompleter(tabManager);
        }
    }

    private AbstractCommand addCommand(AbstractCommand abstractCommand) {
        commands.add(abstractCommand);
        return abstractCommand;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        for (AbstractCommand abstractCommand : commands) {
            if (abstractCommand.getCommand() != null && abstractCommand.getCommand().equalsIgnoreCase(command.getName().toLowerCase())) {
                if (strings.length == 0 || abstractCommand.hasArgs()) {
                    processRequirements(abstractCommand, commandSender, strings);
                    return true;
                }
            } else if (strings.length != 0 && abstractCommand.getParent() != null && abstractCommand.getParent().getCommand().equalsIgnoreCase(command.getName())) {
                String cmd = strings[0];
                String cmd2 = strings.length >= 2 ? String.join(" ", strings[0], strings[1]) : null;
                for (String cmds : abstractCommand.getSubCommand()) {
                    if (cmd.equalsIgnoreCase(cmds) || (cmd2 != null && cmd2.equalsIgnoreCase(cmds))) {
                        processRequirements(abstractCommand, commandSender, strings);
                        return true;
                    }
                }
            }
        }
        commandSender.sendMessage(formatAll("&8[&3PerfectFlight&8] &cThe command you entered does not exist or is spelt incorrectly."));
        return true;
    }

    private void processRequirements(AbstractCommand command, CommandSender sender, String[] strings) {
        FileConfiguration language = instance.getLanguageManager().getConfig();
        if ((sender instanceof Player)) {
            String permissionNode = command.getPermissionNode();
            if (permissionNode == null || sender.hasPermission(command.getPermissionNode())) {
                AbstractCommand.ReturnType returnType = null;
                try {
                    returnType = command.runCommand(instance, sender, strings);
                } catch (MissingPermissionException e) {
                    sender.sendMessage(formatAll(language.getString("General.No Permission").replace("%permission%", e.getMessage())));
                }
                if (returnType == AbstractCommand.ReturnType.SYNTAX_ERROR) {
                    sender.sendMessage(formatAll(language.getString("General.Invalid Command Syntax").replace("%syntax%", command.getSyntax())));
                }
                return;
            }
            sender.sendMessage(formatAll(language.getString("General.No Permission").replace("%permission%", permissionNode)));
            return;
        }
        if (command.isNoConsole()) {
            sender.sendMessage(formatAll(language.getString("General.Must Be Player")));
            return;
        }
        if (command.getPermissionNode() == null || sender.hasPermission(command.getPermissionNode())) {
            AbstractCommand.ReturnType returnType = null;
            try {
                returnType = command.runCommand(instance, sender, strings);
            } catch (MissingPermissionException e) {
                e.printStackTrace();
            }
            if (returnType == AbstractCommand.ReturnType.SYNTAX_ERROR) {
                sender.sendMessage(formatAll(language.getString("General.Invalid Command Syntax").replace("%syntax%", command.getSyntax())));
            }
        }
    }

    public List<AbstractCommand> getCommands() {
        return Collections.unmodifiableList(commands);
    }

}