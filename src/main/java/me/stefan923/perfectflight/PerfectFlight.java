package me.stefan923.perfectflight;

import me.stefan923.perfectflight.commands.CommandManager;
import me.stefan923.perfectflight.language.LanguageManager;
import me.stefan923.perfectflight.listeners.PlayerJoinListener;
import me.stefan923.perfectflight.listeners.PlayerMoveListener;
import me.stefan923.perfectflight.listeners.PlayerQuitListener;
import me.stefan923.perfectflight.settings.SettingsManager;
import me.stefan923.perfectflight.utils.MessageUtils;
import me.stefan923.perfectflight.utils.User;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class PerfectFlight extends JavaPlugin implements MessageUtils {
    private static PerfectFlight instance;

    private SettingsManager settingsManager;
    private LanguageManager languageManager;

    private HashMap<Player, User> users;

    @Override
    public void onEnable() {
        instance = this;

        settingsManager = SettingsManager.getInstance();
        settingsManager.setup(this);

        languageManager = LanguageManager.getInstance();
        languageManager.setup(this);

        users = new HashMap<>();

        sendLogger("&8&l> &7&m------- &8&l( &3&lPerfectFlight &b&lby Stefan923 &8&l) &7&m------- &8&l<");
        sendLogger("&b   Plugin has been initialized.");
        sendLogger("&b   Version: &3v" + getDescription().getVersion());
        sendLogger("&b   Enabled listeners: &3" + enableListeners());
        sendLogger("&b   Enabled commands: &3" + enableCommands());
        sendLogger("&8&l> &7&m------- &8&l( &3&lPerfectFlight &b&lby Stefan923 &8&l) &7&m------- &8&l<");
    }

    private Integer enableListeners() {
        Integer i = 3;
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(this), this);
        pluginManager.registerEvents(new PlayerMoveListener(this), this);
        pluginManager.registerEvents(new PlayerQuitListener(this), this);
        return i;
    }

    private Integer enableCommands() {
        CommandManager commandManager = new CommandManager(this);
        return commandManager.getCommands().size();
    }

    public static PerfectFlight getInstance() {
        return instance;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public void reloadSettingManager() {
        settingsManager.reload();
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    public void reloadLanguageManager() {
        languageManager.reload();
    }

    public HashMap<Player, User> getUsers() {
        return users;
    }

    public User getUser(Player player) {
        if (users.containsKey(player)) {
            return users.get(player);
        }
        return new User(this, player);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

}
