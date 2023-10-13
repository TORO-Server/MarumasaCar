package marumasa.marumasa_car.vehicle.parts;

import marumasa.marumasa_car.vehicle.Vehicle;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

public class Part {
    public final Vector vector;
    public final EntityType entityType;

    public Part(Vector vector, EntityType entityType) {
        this.vector = vector;
        this.entityType = entityType;
    }

    public Entity create(World world, Location location, Vehicle vehicle) {
        final Entity entity = world.spawnEntity(location, entityType);
        vehicle.EntityListAll.add(entity);
        return entity;
    }
}
