package me.stefan923.perfectflight.commands.type;

import me.stefan923.perfectflight.PerfectFlight;
import me.stefan923.perfectflight.commands.AbstractCommand;
import me.stefan923.perfectflight.hooks.checkers.CheckResult;
import me.stefan923.perfectflight.utils.MessageUtils;
import me.stefan923.perfectflight.utils.User;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandFly extends AbstractCommand {

    public CommandFly() {
        super(null, true, "fly");
    }

    @Override
    protected ReturnType runCommand(PerfectFlight instance, CommandSender sender, String... args) {
        Player player = (Player) sender;
        User user = instance.getUser(player);

        FileConfiguration settings = instance.getSettingsManager().getConfig();
        FileConfiguration language = instance.getLanguageManager().getConfig();

        if (user.getFly()) {
            user.setFlight(false);
            user.setFly(false);

            return ReturnType.SUCCESS;
        }

        if (!user.getFly()) {
            CheckResult checkResult = user.canFly();
            if (settings.getBoolean("Hooks.Factions.Auto-Enable") || checkResult.canFly()) {
                user.setFly(true);
            }

            if (checkResult.canFly()) {
                user.setFlight(true);

                return ReturnType.SUCCESS;
            }

            player.sendMessage(MessageUtils.formatAll(language.getString("Command.Fly.Can Not Fly")));
        }

        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(PerfectFlight instance, CommandSender sender, String... args) {
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "perfectflight.fly";
    }

    @Override
    public String getSyntax() {
        return "/fly";
    }

    @Override
    public String getDescription() {
        return "Enable/Disable flight mode.";
    }

}
