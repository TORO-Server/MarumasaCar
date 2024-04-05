package marumasa.marumasa_car.vehicle.parts;

import marumasa.marumasa_car.vehicle.Vehicle;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

public class TrackingDisplay extends Tracking {
    public TrackingDisplay(Vector vector, EntityType entityType) {
        super(vector, entityType);
    }

    @Override
    public Display create(World world, Location location, Vehicle vehicle) {
        final Display display = (Display) super.create(world, location, vehicle);
        display.setTeleportDuration(2);
        display.setViewRange(64);
        return display;
    }
}
