package me.stefan923.perfectflight;

import me.stefan923.perfectflight.commands.CommandManager;
import me.stefan923.perfectflight.hooks.checkers.AbstractChecker;
import me.stefan923.perfectflight.hooks.checkers.CombatLogXChecker;
import me.stefan923.perfectflight.hooks.checkers.factions.FactionsUUIDChecker;
import me.stefan923.perfectflight.hooks.checkers.factions.SaberFactionsChecker;
import me.stefan923.perfectflight.language.LanguageManager;
import me.stefan923.perfectflight.listeners.*;
import me.stefan923.perfectflight.settings.SettingsManager;
import me.stefan923.perfectflight.utils.MessageUtils;
import me.stefan923.perfectflight.utils.User;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PerfectFlight extends JavaPlugin {
    private static PerfectFlight instance;

    private SettingsManager settingsManager;
    private LanguageManager languageManager;

    private List<AbstractChecker> checkers;
    private HashMap<Player, User> users;

    @Override
    public void onEnable() {
        instance = this;

        settingsManager = SettingsManager.getInstance();
        settingsManager.setup(this);

        languageManager = LanguageManager.getInstance();
        languageManager.setup(this);

        checkers = new ArrayList<>();
        users = new HashMap<>();

        MessageUtils.sendLogger("&8&l> &7&m------- &8&l( &3&lPerfectFlight &b&lby Stefan923 &8&l) &7&m------- &8&l<");
        MessageUtils.sendLogger("&b   Plugin has been initialized.");
        MessageUtils.sendLogger("&b   Version: &3v" + getDescription().getVersion());
        MessageUtils.sendLogger("&b   Enabled listeners: &3" + enableListeners());
        MessageUtils.sendLogger("&b   Enabled commands: &3" + enableCommands());
        loadCheckers();
        MessageUtils.sendLogger("&8&l> &7&m------- &8&l( &3&lPerfectFlight &b&lby Stefan923 &8&l) &7&m------- &8&l<");
    }

    private Integer enableListeners() {
        Integer i = 5;
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new EntityDamageListener(this), this);
        pluginManager.registerEvents(new PlayerJoinListener(this), this);
        pluginManager.registerEvents(new PlayerMoveListener(this), this);
        pluginManager.registerEvents(new PlayerQuitListener(this), this);
        pluginManager.registerEvents(new PlayerTeleportListener(this), this);
        return i;
    }

    private Integer enableCommands() {
        CommandManager commandManager = new CommandManager(this);
        return commandManager.getCommands().size();
    }

    private void loadCheckers() {
        FileConfiguration settings = settingsManager.getConfig();

        if (getServer().getPluginManager().getPlugin("Factions") != null && (settings.getBoolean("Hooks.Factions.Auto-Enable") || settings.getBoolean("Hooks.Factions.Auto-Disable Near Enemies.Enabled"))) {
            try {
                Class.forName("com.massivecraft.factions.perms.Relation");
                checkers.add(new FactionsUUIDChecker(this));
                MessageUtils.sendLogger("&b   Enabled checker: &3Factions");
            } catch (ClassNotFoundException e) {
                checkers.add(new SaberFactionsChecker(this));
                MessageUtils.sendLogger("&b   Enabled checker: &3SaberFactions");
            }
        }

        if (getServer().getPluginManager().getPlugin("CombatLogX") != null && settings.getBoolean("Hooks.CombatLogX.Disable Fly On Combat Tag")) {
            checkers.add(new CombatLogXChecker());
            MessageUtils.sendLogger("&b   Enabled checker: &3CombatLogX");
        }
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

    public List<AbstractChecker> getCheckers() {
        return checkers;
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
