package me.stefan923.perfectflight.language;

import me.stefan923.perfectflight.PerfectFlight;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LanguageManager {
    private static final LanguageManager instance = new LanguageManager();
    private FileConfiguration config;
    private File cfile;

    public static LanguageManager getInstance() {
        return instance;
    }

    public void setup(PerfectFlight instance) {
        cfile = new File(instance.getDataFolder(), "language.yml");
        config = YamlConfiguration.loadConfiguration(cfile);

        config.options().header("PerfectFlight by Stefan923\n");
        config.addDefault("Command.Fly.Can Not Fly", "&8(&3!&8) &cYou &4can't &cenable your flight mode here!");
        config.addDefault("Command.Fly.Enabled", "&8(&3!&8) &fYour flight mode has been &aenabled&f!");
        config.addDefault("Command.Fly.Disabled", "&8(&3!&8) &fYour flight mode has been &cdisabled&f!");
        config.addDefault("Auto Flight Mode.Enabled", "&8(&3!&8) &fYour flight mode has been &aenabled&f!");
        config.addDefault("Auto Flight Mode.Disabled.Can Not Fly Here", "&8(&3!&8) &fYour flight mode has been &cdisabled&f because you can't fly over this territory!");
        config.addDefault("Auto Flight Mode.Disabled.In Combat", "&8(&3!&8) &fYour flight mode has been &cdisabled&f because you are in combat!");
        config.addDefault("Auto Flight Mode.Disabled.Nearby Enemies", "&8(&3!&8) &fYour flight mode has been &cdisabled&f because there are nearby enemies!");
        config.addDefault("Auto Flight Mode.No Fall Damage", "&8(&3!&8) &fYou won't take any &bfall damage &ffor &3%duration%&f!");
        config.addDefault("General.Must Be Player", "&8(&3!&8) &cYou must be a player to do this!");
        config.addDefault("General.Invalid Command Syntax", "&8(&3!&8) &cInvalid Syntax or you have no permission!\n&8(&3!&8) &fThe valid syntax is: &b%syntax%");
        config.addDefault("General.No Permission", "&8(&3!&8) &cYou need the &4%permission% &cpermission to do that!");
        config.addDefault("General.Word.Second", "second");
        config.addDefault("General.Word.Seconds", "seconds");
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
