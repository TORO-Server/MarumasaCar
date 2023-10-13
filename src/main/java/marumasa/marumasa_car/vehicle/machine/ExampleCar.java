package marumasa.marumasa_car.vehicle.machine;

import marumasa.marumasa_car.vehicle.Vehicle;
import marumasa.marumasa_car.vehicle.VehicleUtils;
import marumasa.marumasa_car.vehicle.parts.Body;
import marumasa.marumasa_car.vehicle.parts.MainSeat;
import marumasa.marumasa_car.vehicle.parts.Seat;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

import marumasa.marumasa_car.vehicle.parts.Part;
import org.bukkit.util.Vector;

public class ExampleCar extends Vehicle {
    public static final String tag = "marumasa.example";

    public ExampleCar(ArmorStand stand, JavaPlugin pl) {
        super(stand, pl);
    }

    @Override
    public List<Part> partsList() {
        return new ArrayList<>() {{
            add(new Seat(new Vector(0.5, 0.5, 0.5)));
            add(new Seat(new Vector(0.5, 0.5, -0.5)));
            add(new Seat(new Vector(-0.5, 0.5, -0.5)));
            add(new Body(new Vector(0, 0.5, 0), Material.DISC_FRAGMENT_5));
            add(new MainSeat(new Vector(-0.5, 0.5, 0.5)));
        }};
    }
}
