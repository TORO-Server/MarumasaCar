package marumasa.marumasa_car.vehicle.parts;

import marumasa.marumasa_car.vehicle.Vehicle;
import marumasa.marumasa_car.vehicle.VehicleController;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

public class Seat extends TrackingDisplay {

    public Interaction seat;
    public final float width;
    public final float height;

    public Seat(Vector vector) {
        super(vector, EntityType.ITEM_DISPLAY);
        width = 1;
        height = 1;
    }

    public Seat(Vector vector, float width, float height) {
        super(vector, EntityType.ITEM_DISPLAY);
        this.width = width;
        this.height = height;
    }

    @Override
    public Display create(World world, Location location, Vehicle vehicle) {
        final ItemDisplay itemDisplay = (ItemDisplay) super.create(world, location, vehicle);

        itemDisplay.setTeleportDuration(2);

        seat = (Interaction) new Part(
                vector,
                EntityType.INTERACTION
        ).create(world, location, vehicle);

        seat.setInteractionWidth(width);
        seat.setInteractionHeight(height);

        itemDisplay.addPassenger(seat);
        VehicleController.SeatList.add(seat);

        return itemDisplay;
    }
}
