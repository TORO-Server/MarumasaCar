package marumasa.marumasa_car.vehicle.machine;

import marumasa.marumasa_car.vehicle.advanced.AdvancedVehicle;
import marumasa.marumasa_car.vehicle.parts.Body;
import marumasa.marumasa_car.vehicle.parts.Part;
import marumasa.marumasa_car.vehicle.parts.Seat;
import marumasa.marumasa_car.vehicle.parts.advanced.ATMainSeat;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class ATSedanS13 extends AdvancedVehicle {
    public ATSedanS13(ArmorStand stand, JavaPlugin pl) {
        super(stand, pl);
    }

    @Override
    public List<Part> generateParts() {
        return new ArrayList<>() {{
            // 助手席
            add(new Seat(new Vector(0.5, 0.5, 0.25), 1, 1));
            // 運転席
            add(new ATMainSeat(new Vector(-0.5, 0.5, 0.25), 1, 1, advancedVehicleInstance));
            // 後部座席
            add(new Seat(new Vector(0.5, 0.5, -0.5), 1, 1));
            add(new Seat(new Vector(-0.5, 0.5, -0.5), 1, 1));
            add(new Body(
                    Material.DISC_FRAGMENT_5,
                    new Vector3f(0f, 0f, 0f),
                    new Vector3f(2.4f),
                    21
            ));
        }};
    }
}
