package marumasa.marumasa_car.vehicle;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class TestCar extends Car {

    @Override
    public List<Part> partsList() {
        return new ArrayList<>() {{
            add(new Part(new Vector(0.5, 0.5, 0.5), EntityType.ITEM_DISPLAY, true));
            add(new Part(new Vector(0.5, 0.5, -0.5), EntityType.ITEM_DISPLAY, true));
            add(new Part(new Vector(-0.5, 0.5, -0.5), EntityType.ITEM_DISPLAY, true));
            add(new Part(new Vector(-0.5, 0.5, 0.5), EntityType.ITEM_DISPLAY, true));
            add(new Part(new Vector(0, 0.5, 0), EntityType.ITEM_DISPLAY, false));
        }};
    }


    public TestCar(ArmorStand armorStand) {
        super(armorStand);
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
