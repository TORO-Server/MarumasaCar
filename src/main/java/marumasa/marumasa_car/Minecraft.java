package marumasa.marumasa_car;

import marumasa.marumasa_car.vehicle.machine.MinumaCar;
import marumasa.marumasa_car.vehicle.Machine;
import marumasa.marumasa_car.vehicle.machine.SalmonCar;
import marumasa.marumasa_car.vehicle.machine.SimpleTruck;
import marumasa.marumasa_car.vehicle.machine.SimpleBus;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public final class Minecraft extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Config config = new Config(this);
        final Server server = getServer();
        server.getPluginManager().registerEvents(new Events(config, this), this);


        Machine.register("marumasa.minuma", MinumaCar::new);
        Machine.register("fhrk.simpletruck", SimpleTruck::new);
        Machine.register("fhrk.simplebus", SimpleBus::new);
        Machine.register("salmon.car", SalmonCar::new);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
