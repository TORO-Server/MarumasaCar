package marumasa.marumasa_car.vehicle.machine;

import marumasa.marumasa_car.vehicle.Vehicle;
import marumasa.marumasa_car.vehicle.parts.Body;
import marumasa.marumasa_car.vehicle.parts.MainSeat;
import marumasa.marumasa_car.vehicle.parts.Part;
import marumasa.marumasa_car.vehicle.parts.Seat;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class SimpleBus extends Vehicle {
    public SimpleBus(ArmorStand armorStand, JavaPlugin plugin) {
        super(armorStand, plugin);
    }

    @Override
    public List<Part> generateParts() {
        return new ArrayList<>() {{
            add(new MainSeat(new Vector(-0.7, 0.6, 3.5), 0.8f, 1));
            add(new Seat(new Vector(-1, 0.8, 1.5)));
            add(new Seat(new Vector(-1, 0.8, 0.3)));
            add(new Seat(new Vector(-1, 0.8, -1)));
            add(new Seat(new Vector(-1, 0.8, -2.3)));
            add(new Seat(new Vector(-1, 0.8, -3.6)));
            add(new Seat(new Vector(0, 0.8, -3.6)));
            add(new Seat(new Vector(1, 0.8, -3.6)));
            add(new Seat(new Vector(1, 0.8, -2.3)));
            add(new Seat(new Vector(1, 0.8, 0.5)));
            add(new Seat(new Vector(1, 0.8, 1.8)));
            add(new Body(
                    Material.DISC_FRAGMENT_5,
                    new Vector3f(0f, 0f, 0f),
                    new Vector3f(10f),
                    12
            ));
        }};
    }
}

