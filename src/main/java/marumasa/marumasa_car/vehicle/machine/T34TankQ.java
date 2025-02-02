package marumasa.marumasa_car.vehicle.machine;

import marumasa.marumasa_car.vehicle.Vehicle;
import marumasa.marumasa_car.vehicle.parts.Body;
import marumasa.marumasa_car.vehicle.parts.MainSeat;
import marumasa.marumasa_car.vehicle.parts.Part;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class T34TankQ extends Vehicle {
    public T34TankQ(ArmorStand stand, JavaPlugin pl) {super(stand, pl);}

    @Override
    public List<Part> generateParts() {
        return new ArrayList<>() {{
            // ハッチ上
            add(new MainSeat(new Vector(0.3, 2.2, 0), 1, 1));
            add(new Body(
                    Material.DISC_FRAGMENT_5,
                    new Vector3f(0f, 0.1f, 0f),
                    new Vector3f(3.7f),
                    24
            ));
        }};
    }
}
