package marumasa.marumasa_car.vehicle.machine;

import marumasa.marumasa_car.vehicle.Vehicle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

import static marumasa.marumasa_car.vehicle.VehicleParts.*;

public class TestCar extends Vehicle {

    public TestCar(ArmorStand stand, JavaPlugin pl) {
        super(stand, pl);
    }

    @Override
    public List<superPart> partsList() {
        return new ArrayList<>() {{
            add(new superPart(new Vector(0.5, 0.5, 0.5), true));
            add(new superPart(new Vector(0.5, 0.5, -0.5), true));
            add(new superPart(new Vector(-0.5, 0.5, -0.5), true));
            add(new superPart(new Vector(-0.5, 0.5, 0.5), true));
            add(new superPart(new Vector(0, 0.5, 0), false));
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
