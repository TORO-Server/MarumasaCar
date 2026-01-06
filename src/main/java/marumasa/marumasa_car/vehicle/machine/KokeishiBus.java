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

public class KokeishiBus extends Vehicle {
    public KokeishiBus(ArmorStand stand, JavaPlugin pl) {super(stand, pl);}

    @Override
    public List<Part> generateParts() {
        return new ArrayList<>() {{
            //車体
            add(new Body(
                    Material.DISC_FRAGMENT_5,
                    new Vector3f(0f, 0f, 0f),
                    new Vector3f(4f),
                    41
            ));
            //運転席
            add(new MainSeat(new Vector(-1, 1, 5), 1, 1));
            //タイヤの上の席
            add(new Seat(new Vector(-1, 1.3, 3.5), 1, 1));
            add(new Seat(new Vector(1, 1.3, 3.5), 1, 1));
            //優先席
            add(new Seat(new Vector(1, 1, 2), 1, 1));
            add(new Seat(new Vector(1, 1, 1), 1, 1));
            //座席
            add(new Seat(new Vector(-1, 1.3, 2), 1, 1));
            add(new Seat(new Vector(-1, 1.3, 0.7), 1, 1));
            add(new Seat(new Vector(-1, 1.3, -0.5), 1, 1));
            add(new Seat(new Vector(1, 1.3, -2), 1, 1));
            add(new Seat(new Vector(-1, 1.3, -2), 1, 1));
            add(new Seat(new Vector(1, 1.3, -3.5), 1, 1));
            add(new Seat(new Vector(-1, 1.3, -3.5), 1, 1));
            add(new Seat(new Vector(1, 1.5, -5), 1, 1));
            add(new Seat(new Vector(0, 1.5, -5), 1, 1));
            add(new Seat(new Vector(-1, 1.5, -5), 1, 1));
        }};
    }
}
