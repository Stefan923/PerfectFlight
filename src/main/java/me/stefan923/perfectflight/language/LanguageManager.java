package me.stefan923.perfectflight.language;

import me.stefan923.perfectflight.PerfectFlight;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LanguageManager {
    private static LanguageManager instance = new LanguageManager();
    private FileConfiguration config;
    private File cfile;

    public static LanguageManager getInstance() {
        return instance;
    }

    public void setup(PerfectFlight instance) {
        cfile = new File(instance.getDataFolder(), "language.yml");
        config = YamlConfiguration.loadConfiguration(cfile);

        config.options().header("PerfectFlight by Stefan923\n");
        config.addDefault("General.Must Be Player", "&8(&3!&8) &cYou must be a player to do this!");
        config.addDefault("General.Invalid Command Syntax", "&8(&3!&8) &cInvalid Syntax or you have no permission!\n&8(&3!&8) &fThe valid syntax is: &b%syntax%");
        config.addDefault("General.No Permission", "&8(&3!&8) &cYou need the &4%permission% &cpermission to do that!");
        config.options().copyDefaults(true);
        save();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    private void save() {
        try {
            config.save(cfile);
        } catch (IOException e) {
            Bukkit.getLogger().severe(ChatColor.RED + "File 'language.yml' couldn't be saved!");
        }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(cfile);
    }
}
