package marumasa.marumasa_car.vehicle;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
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
            add(new Part(new Vector(0.5, 0.5, 0.5), true));
            add(new Part(new Vector(0.5, 0.5, -0.5), true));
            add(new Part(new Vector(-0.5, 0.5, -0.5), true));
            add(new Part(new Vector(-0.5, 0.5, 0.5), true));
            add(new Part(new Vector(0, 0.5, 0), false));
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
