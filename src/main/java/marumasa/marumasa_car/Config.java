package marumasa.marumasa_car;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    public Config(final Minecraft plugin) {
        plugin.saveDefaultConfig();
        FileConfiguration config = plugin.getConfig();
    }
}
