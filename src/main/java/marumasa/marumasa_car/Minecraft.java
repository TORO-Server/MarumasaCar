package marumasa.marumasa_car;

import marumasa.marumasa_car.vehicle.machine.SmoothExampleCar;
import marumasa.marumasa_car.vehicle.machine.*;
import marumasa.marumasa_car.vehicle.Machine;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public final class Minecraft extends JavaPlugin {
    // デバッグ用(PartからもLoggerを使いたかったので)
    private static Minecraft marumasaCarInstance;
    public static Minecraft getInstance() {return marumasaCarInstance;}
    public Minecraft() {marumasaCarInstance = this;}

    @Override
    public void onEnable() {
        // Plugin startup logic
        Config config = new Config(this);
        final Server server = getServer();
        server.getPluginManager().registerEvents(new Events(config, this), this);

        Machine.register("marumasa.minuma", MinumaCar::new);
        Machine.register("marumasa.smooth_example", SmoothExampleCar::new);
        Machine.register("fhrk.simpletruck", SimpleTruck::new);
        Machine.register("fhrk.simplebus", SimpleBus::new);
        Machine.register("salmon.car", SalmonCar::new);
        Machine.register("toilet.sedans13", SedanS13::new);
        Machine.register("toilet.t34tankq", T34TankQ::new);
        Machine.register("salmon.keitruck", KeiTruck::new);
        Machine.register("tetsuota.luup", TetsuotaLuup::new);
        Machine.register("toilet.t72b3", T72B3::new);
        Machine.register("toilet.rx7fd3se", Rx7fd3se::new);
        Machine.register("toilet.180sx", Car180sx::new);
        Machine.register("kokeishi.bus", KokeishiBus::new);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
