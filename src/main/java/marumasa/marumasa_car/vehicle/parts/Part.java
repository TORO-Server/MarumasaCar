package marumasa.marumasa_car.vehicle.parts;

import marumasa.marumasa_car.vehicle.Vehicle;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Interaction;
import org.bukkit.util.Vector;

import static marumasa.marumasa_car.vehicle.VehicleController.InteractionLink;
import static marumasa.marumasa_car.vehicle.VehicleController.LoadingParts;

public class Part {
    public final Vector vector;
    public final EntityType entityType;

    public Part(Vector vector, EntityType entityType) {
        this.vector = vector;
        this.entityType = entityType;
    }

    public Entity create(World world, Location location, Vehicle vehicle) {
        final Entity entity = world.spawnEntity(location, entityType);
        entity.addScoreboardTag("marumasa.vehicle.part");
        vehicle.EntityListAll.add(entity);

        LoadingParts.add(entity);

        if (entity instanceof Interaction interaction)
            InteractionLink.put(interaction, vehicle.body);

        return entity;
    }

    public void tick(Location location, float yaw, Entity entity) {
    }
}
