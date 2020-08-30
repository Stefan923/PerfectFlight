package me.stefan923.perfectflight.commands.type;

import me.stefan923.perfectflight.PerfectFlight;
import me.stefan923.perfectflight.commands.AbstractCommand;
import me.stefan923.perfectflight.utils.MessageUtils;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandReload extends AbstractCommand implements MessageUtils {

    public CommandReload(AbstractCommand abstractCommand) {
        super(abstractCommand, false, "reload");
    }

    @Override
    protected ReturnType runCommand(PerfectFlight instance, CommandSender sender, String... args) {
        if (args.length != 2)
            return ReturnType.SYNTAX_ERROR;

        if (args[1].equalsIgnoreCase("all")) {
            instance.reloadSettingManager();
            instance.reloadLanguageManager();
            sender.sendMessage(formatAll("&8[&3PerfectFlight&8] &fYou have successfully reloaded &ball &fmodules!"));
            return ReturnType.SUCCESS;
        }

        if (args[1].equalsIgnoreCase("settings")) {
            instance.reloadSettingManager();
            sender.sendMessage(formatAll("&8[&3PerfectFlight&8] &fYou have successfully reloaded &bsettings &fmodule!"));
            return ReturnType.SUCCESS;
        }

        if (args[1].equalsIgnoreCase("languages")) {
            instance.reloadLanguageManager();
            sender.sendMessage(formatAll("&8[&3PerfectFlight&8] &fYou have successfully reloaded &blanguages &fmodule!"));
            return ReturnType.SUCCESS;
        }

        return ReturnType.SYNTAX_ERROR;
    }

    @Override
    protected List<String> onTab(PerfectFlight instance, CommandSender sender, String... args) {
        if (sender.hasPermission("perfectflight.admin") && args.length == 2 && args[0].toLowerCase().equalsIgnoreCase("reload"))
            return Stream.of("all", "languages", "settings").filter(string -> string.startsWith(args[1].toLowerCase())).collect(Collectors.toList());

        return null;
    }

    @Override
    public String getPermissionNode() {
        return "perfectflight.admin";
    }

    @Override
    public String getSyntax() {
        return "/perfectflight reload <all|settings|language>";
    }

    @Override
    public String getDescription() {
        return "Reloads plugin settings.";
    }

}
