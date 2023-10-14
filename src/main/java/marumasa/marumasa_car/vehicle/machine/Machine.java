package marumasa.marumasa_car.vehicle.machine;

import marumasa.marumasa_car.vehicle.Vehicle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

public class Machine {

    private static final Map<String, BiFunction<ArmorStand, JavaPlugin, Vehicle>> MachineTag = new HashMap<>();

    public static void register(String tag, BiFunction<ArmorStand, JavaPlugin, Vehicle> vehicle) {
        MachineTag.put(tag, vehicle);
    }

    public static void tryLoad(Set<String> tags, ArmorStand stand, JavaPlugin pl) {
        for (String tag : tags) {
            BiFunction<ArmorStand, JavaPlugin, Vehicle> vehicle = MachineTag.get(tag);
            if (vehicle == null) continue;
            vehicle.apply(stand, pl);
            return;
        }
    }
}
