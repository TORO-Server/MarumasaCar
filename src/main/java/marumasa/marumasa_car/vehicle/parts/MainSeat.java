package marumasa.marumasa_car.vehicle.parts;

import marumasa.marumasa_car.vehicle.Vehicle;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.util.Vector;

public class MainSeat extends Seat {
    public MainSeat(Vector vector) {
        super(vector);
    }

    @Override
    public Display create(World world, Location location, Vehicle vehicle) {
        final ItemDisplay itemDisplay = (ItemDisplay) super.create(world, location, vehicle);

        vehicle.mainSeat = super.seat;

        return itemDisplay;
    }
}
