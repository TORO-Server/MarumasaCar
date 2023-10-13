package marumasa.marumasa_car.vehicle.machine;

import marumasa.marumasa_car.vehicle.parts.MainSeatPart;
import marumasa.marumasa_car.vehicle.parts.Part;
import marumasa.marumasa_car.vehicle.parts.SeatPart;
import marumasa.marumasa_car.vehicle.Vehicle;
import marumasa.marumasa_car.vehicle.parts.BodyPart;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class TestCar extends Vehicle {

    public TestCar(ArmorStand stand, JavaPlugin pl) {
        super(stand, pl);
    }

    @Override
    public List<Part> partsList() {
        return new ArrayList<>() {{
            add(new SeatPart(new Vector(0.5, 0.5, 0.5)));
            add(new SeatPart(new Vector(0.5, 0.5, -0.5)));
            add(new SeatPart(new Vector(-0.5, 0.5, -0.5)));
            add(new BodyPart(new Vector(0, 0.5, 0), Material.DISC_FRAGMENT_5));
            add(new MainSeatPart(new Vector(-0.5, 0.5, 0.5)));
        }};
    }

    @Override
    public void run() {
        super.run();
    }

    @Override
    public void remove() {
        super.remove();
    }
}
