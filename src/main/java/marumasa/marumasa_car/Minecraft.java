package marumasa.marumasa_car;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public final class Minecraft extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Config config = new Config(this);
        final Server server = getServer();
        server.getPluginManager().registerEvents(new Events(config, this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
