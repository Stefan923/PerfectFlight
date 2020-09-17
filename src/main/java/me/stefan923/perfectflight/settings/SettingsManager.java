package me.stefan923.perfectflight.settings;

import me.stefan923.perfectflight.PerfectFlight;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class SettingsManager {

    private static final SettingsManager instance = new SettingsManager();
    private FileConfiguration config;
    private File cfile;

    public static SettingsManager getInstance() {
        return instance;
    }

    public void setup(PerfectFlight instance) {
        cfile = new File(instance.getDataFolder(), "settings.yml");
        config = YamlConfiguration.loadConfiguration(cfile);

        config.options().header("PerfectFlight by Stefan923\n");
        config.addDefault("Fly Settings.Disable Fall Damage.Enabled", true);
        config.addDefault("Fly Settings.Disable Fall Damage.Duration In Seconds", 10);
        config.addDefault("Enabled Commands.Bypass", true);
        config.addDefault("Enabled Commands.Fly", true);
        config.addDefault("Hooks.CombatLogX.Disable Fly On Combat Tag", false);
        config.addDefault("Hooks.Factions.Auto-Enable", false);
        config.addDefault("Hooks.Factions.Auto-Disable Near Enemies.Enabled", false);
        config.addDefault("Hooks.Factions.Auto-Disable Near Enemies.Check Radius", 32);
        config.options().copyDefaults(true);
        save();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void save() {
        try {
            config.save(cfile);
        } catch (IOException e) {
            Bukkit.getLogger().severe(ChatColor.RED + "File 'settings.yml' couldn't be saved!");
        }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(cfile);
    }
}
