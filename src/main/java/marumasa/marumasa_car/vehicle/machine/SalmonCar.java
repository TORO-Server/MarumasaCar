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

public class SalmonCar extends Vehicle {

    public SalmonCar(ArmorStand stand, JavaPlugin pl) {
        super(stand, pl);
    }

    @Override
    public List<Part> generateParts() {
        return new ArrayList<>() {{ // x:左右 y:高さ z:前後
            add(new MainSeat(new Vector(-0.5, .85, -0.15), 0.8f, 1)); //1列目 右
            add(new Seat(new Vector(0.5, .85, -1.5), 0.8f, 1)); // 2列目
            add(new Seat(new Vector(0.4, .85, -2.45), 0.8f, 1)); //3列目
            add(new Seat(new Vector(0.5, .85, -0.15), 0.8f, 1)); //1列目 左
            add(new Seat(new Vector(-0.5, .85, -1.5), 0.8f, 1)); //2列目
            add(new Seat(new Vector(-0.4, .85, -2.45), 0.8f, 1)); //3列目
            add(new Body(
                    Material.DISC_FRAGMENT_5,
                    new Vector3f(0f, 0f, 0.65f),
                    new Vector3f(2.6f),
                    16
            ));
        }};
    }
}
