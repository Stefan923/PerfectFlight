package me.stefan923.perfectflight.commands.type;

import me.stefan923.perfectflight.PerfectFlight;
import me.stefan923.perfectflight.commands.AbstractCommand;
import me.stefan923.perfectflight.utils.MessageUtils;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandPerfectFlight extends AbstractCommand {

    public CommandPerfectFlight() {
        super(null, false, "perfectflight");
    }

    @Override
    protected ReturnType runCommand(PerfectFlight instance, CommandSender sender, String... args) {
        sender.sendMessage(MessageUtils.formatAll(" "));
        MessageUtils.sendCenteredMessage(sender, MessageUtils.formatAll("&8&m--+----------------------------------------+--&r"));
        MessageUtils.sendCenteredMessage(sender, MessageUtils.formatAll("&3&lPerfectFlight &f&lv" + instance.getDescription().getVersion()));
        MessageUtils.sendCenteredMessage(sender, MessageUtils.formatAll("&8&l» &fPlugin author: &bStefan923"));
        MessageUtils.sendCenteredMessage(sender, MessageUtils.formatAll(" "));
        MessageUtils.sendCenteredMessage(sender, MessageUtils.formatAll("&8&l» &fAdds a fully customizable flight system to Minecraft."));
        MessageUtils.sendCenteredMessage(sender, MessageUtils.formatAll("&8&m--+----------------------------------------+--&r"));
        sender.sendMessage(MessageUtils.formatAll(" "));

        return ReturnType.SUCCESS;
    }

    @Override
    protected List<String> onTab(PerfectFlight instance, CommandSender sender, String... args) {
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            if (sender.hasPermission("perfectflight.admin")) {
                list.addAll(Stream.of("reload").filter(string -> string.startsWith(args[0].toLowerCase())).collect(Collectors.toList()));
            }
            if (sender.hasPermission("perfectflight.bypass")) {
                list.addAll(Stream.of("bypass").filter(string -> string.startsWith(args[0].toLowerCase())).collect(Collectors.toList()));
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
