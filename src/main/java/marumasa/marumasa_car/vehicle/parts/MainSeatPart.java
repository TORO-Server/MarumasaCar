package marumasa.marumasa_car.vehicle.parts;

import marumasa.marumasa_car.vehicle.Vehicle;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.util.Vector;

public class MainSeatPart extends SeatPart {
    public MainSeatPart(Vector vector) {
        super(vector);
    }

    @Override
    public Entity create(World world, Location location, Vehicle vehicle) {
        final ItemDisplay itemDisplay = (ItemDisplay) super.create(world, location, vehicle);

        vehicle.mainSeat = super.seat;

        return itemDisplay;
    }
}
