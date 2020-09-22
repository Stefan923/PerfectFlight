package me.stefan923.perfectflight.commands.type;

import me.stefan923.perfectflight.PerfectFlight;
import me.stefan923.perfectflight.commands.AbstractCommand;
import me.stefan923.perfectflight.utils.MessageUtils;
import me.stefan923.perfectflight.utils.User;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandBypass extends AbstractCommand implements MessageUtils {

    public CommandBypass(AbstractCommand abstractCommand) {
        super(abstractCommand, true, "bypass");
    }

    @Override
    protected ReturnType runCommand(PerfectFlight instance, CommandSender sender, String... args) {
        FileConfiguration language = instance.getLanguageManager().getConfig();
        User user = instance.getUser((Player) sender);

        if (user.isBypassing()) {
            user.setBypassing(false);
            sender.sendMessage(formatAll(language.getString("Command.Bypass.Disabled")));

            return ReturnType.SUCCESS;
        }

        user.setBypassing(true);
        sender.sendMessage(formatAll(language.getString("Command.Bypass.Enabled")));

        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(PerfectFlight instance, CommandSender sender, String... args) {
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "perfectflight.bypass";
    }

    @Override
    public String getSyntax() {
        return "/perfectflight bypass";
    }

    @Override
    public String getDescription() {
        return "Enables bypass mode for fly checker.";
    }

}
