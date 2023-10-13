package marumasa.marumasa_car.vehicle.parts;

import marumasa.marumasa_car.vehicle.Vehicle;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

public class TrackingPart extends Part {
    public TrackingPart(Vector vector, EntityType entityType) {
        super(vector, entityType);
    }

    @Override
    public Entity create(World world, Location location, Vehicle vehicle) {
        final Entity entity = super.create(world, location, vehicle);
        vehicle.EntityListTracking.add(entity);
        return entity;
    }
}
