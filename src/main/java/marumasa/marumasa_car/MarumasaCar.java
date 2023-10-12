package marumasa.marumasa_car;

import org.bukkit.Server;
import org.bukkit.entity.Interaction;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MarumasaCar extends JavaPlugin {

    public static final Map<String, List<String>> CarList = new HashMap<>();

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
