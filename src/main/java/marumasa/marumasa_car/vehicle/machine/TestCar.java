package marumasa.marumasa_car.vehicle.machine;

import marumasa.marumasa_car.vehicle.parts.MainSeat;
import marumasa.marumasa_car.vehicle.parts.Part;
import marumasa.marumasa_car.vehicle.parts.Seat;
import marumasa.marumasa_car.vehicle.Vehicle;
import marumasa.marumasa_car.vehicle.parts.Body;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class TestCar extends Vehicle {

    public static final String tag = "marumasa.test";

    public TestCar(ArmorStand stand, JavaPlugin pl) {
        super(stand, pl);
    }

    @Override
    public List<Part> partsList() {
        return new ArrayList<>() {{
            add(new Seat(new Vector(0.4, 0.5, 0.5), 0.7f, 0.5f));
            add(new Seat(new Vector(0.4, 0.5, -0.5), 0.7f, 0.5f));
            add(new Seat(new Vector(-0.4, 0.5, -0.5), 0.7f, 0.5f));
            add(new MainSeat(new Vector(-0.4, 0.5, 0.5), 0.7f, 0.5f));
            add(new Body(new Vector(0, 0.5, 0), Material.DISC_FRAGMENT_5, 1));
        }};
    }

    @Override
    public void remove() {
        super.remove();

    }
}
