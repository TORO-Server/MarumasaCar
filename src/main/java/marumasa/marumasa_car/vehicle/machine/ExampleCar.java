package marumasa.marumasa_car.vehicle.machine;

import marumasa.marumasa_car.vehicle.Vehicle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

import marumasa.marumasa_car.vehicle.parts.Part;

public class ExampleCar extends Vehicle {

    public ExampleCar(ArmorStand stand, JavaPlugin pl) {
        super(stand, pl);
    }

    @Override
    public List<Part> partsList() {
        return new ArrayList<>() {{
        }};
    }
}
