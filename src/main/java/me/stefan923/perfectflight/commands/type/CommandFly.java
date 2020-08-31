package me.stefan923.perfectflight.commands.type;

import me.stefan923.perfectflight.PerfectFlight;
import me.stefan923.perfectflight.commands.AbstractCommand;
import me.stefan923.perfectflight.utils.MessageUtils;
import me.stefan923.perfectflight.utils.User;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandFly extends AbstractCommand implements MessageUtils {

    public CommandFly() {
        super(null, true, "fly");
    }

    @Override
    protected ReturnType runCommand(PerfectFlight instance, CommandSender sender, String... args) {
        Player player = (Player) sender;
        User user = instance.getUser(player);

        FileConfiguration language = instance.getLanguageManager().getConfig();

        if (player.getAllowFlight()) {
            user.setFlight(false);

            return ReturnType.SUCCESS;
        }

        if (user.canFly()) {
            user.setFlight(true);

            return ReturnType.SUCCESS;
        }

        player.sendMessage(formatAll(language.getString("Command.Fly.Can Not Fly")));
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
