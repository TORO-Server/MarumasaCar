package marumasa.marumasa_car.vehicle;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VehicleUtils {

    // Map<乗っているエンティティ,乗られているエンティティ>
    public static Map<Entity, Entity> removePassenger(Entity entity) {
        final Map<Entity, Entity> passengerData = new HashMap<>();
        for (Entity ride : entity.getPassengers()) {
            entity.removePassenger(ride);
            passengerData.put(ride, entity);
        }
        return passengerData;
    }

    public static void addPassenger(Map<Entity, Entity> passengerData) {
        for (Entity key : passengerData.keySet()) {
            passengerData.get(key).addPassenger(key);
        }
    }

    public static boolean isVehicleParts(Set<String> tags) {
        return tags.contains("marumasa.vehicle.part");
    }

    public static boolean isSolid(Location loc) {
        return loc.getBlock().getType().isSolid();
    }

    public static boolean isOccluding(Location loc) {
        return loc.getBlock().getType().isOccluding();
    }
}
