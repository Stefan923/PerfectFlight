package me.stefan923.perfectflight.commands.type;

import me.stefan923.perfectflight.PerfectFlight;
import me.stefan923.perfectflight.commands.AbstractCommand;
import me.stefan923.perfectflight.utils.MessageUtils;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandPerfectFlight extends AbstractCommand implements MessageUtils {

    public CommandPerfectFlight() {
        super(null, false, "perfectflight");
    }

    @Override
    protected ReturnType runCommand(PerfectFlight instance, CommandSender sender, String... args) {
        sender.sendMessage(formatAll(" "));
        sendCenteredMessage(sender, formatAll("&8&m--+----------------------------------------+--&r"));
        sendCenteredMessage(sender, formatAll("&3&lPerfectFlight &f&lv" + instance.getDescription().getVersion()));
        sendCenteredMessage(sender, formatAll("&8&l» &fPlugin author: &bStefan923"));
        sendCenteredMessage(sender, formatAll(" "));
        sendCenteredMessage(sender, formatAll("&8&l» &fAdds a fully customizable flight system to Minecraft."));
        sendCenteredMessage(sender, formatAll("&8&m--+----------------------------------------+--&r"));
        sender.sendMessage(formatAll(" "));

        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(PerfectFlight instance, CommandSender sender, String... args) {
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            if (sender.hasPermission("perfectflight.admin")) {
                list.addAll(Stream.of("reload").filter(string -> string.startsWith(args[0].toLowerCase())).collect(Collectors.toList()));
            }
            return list.isEmpty() ? null : list;
        }

        return null;
    }

    @Override
    public String getPermissionNode() {
        return null;
    }

    @Override
    public String getSyntax() {
        return "/perfectflight";
    }

    @Override
    public String getDescription() {
        return "Displays plugin info";
    }

}
