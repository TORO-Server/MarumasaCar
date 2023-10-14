package marumasa.marumasa_car.vehicle.parts;

import marumasa.marumasa_car.vehicle.Vehicle;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import java.util.Map;

import static marumasa.marumasa_car.vehicle.VehicleUtils.addPassenger;
import static marumasa.marumasa_car.vehicle.VehicleUtils.removePassenger;

public class Tracking extends Part {
    public Tracking(Vector vector, EntityType entityType) {
        super(vector, entityType);
    }

    @Override
    public Entity create(World world, Location location, Vehicle vehicle) {
        final Entity entity = super.create(world, location, vehicle);
        vehicle.EntityListTracking.add(entity);
        return entity;
    }

    @Override
    public void tick(Location location, float yaw, Entity entity) {
        Map<Entity, Entity> passenger = removePassenger(entity);
        final Location loc = location.clone();
        loc.add(vector.clone().rotateAroundY(-Math.toRadians(loc.getYaw())));
        entity.teleport(loc);
        addPassenger(passenger);
    }
}
