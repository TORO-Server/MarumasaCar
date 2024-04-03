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

public class ExampleCar extends Vehicle {

    public ExampleCar(ArmorStand stand, JavaPlugin pl) {
        super(stand, pl);
    }

    @Override
    public List<Part> generateParts() {
        return new ArrayList<>() {{
            add(new MainSeat(new Vector(0.48, 0.5, 0.45), 0.8f, 1));
            add(new Seat(new Vector(0.48, 0.5, -0.7), 0.8f, 1));
            add(new Seat(new Vector(-0.48, 0.5, -0.7), 0.8f, 1));
            add(new Seat(new Vector(-0.48, 0.5, 0.45), 0.8f, 1));


            add(new Body(
                    Material.QUARTZ_SLAB,
                    new Vector3f(0f, 0.5f, 3f),
                    new Vector3f(0f, 0f, 0f),
                    new Vector3f(1f)
            ));
            add(new Body(
                    Material.GRAY_WOOL,
                    new Vector3f(0f, 0f, 2f),
                    new Vector3f(0f, 0f, 0f),
                    new Vector3f(1f)
            ));
            add(new Body(
                    Material.GRAY_WOOL,
                    new Vector3f(0f, 0f, 1f),
                    new Vector3f(0f, 0f, 0f),
                    new Vector3f(1f)
            ));
            add(new Body(
                    Material.GRAY_WOOL,
                    new Vector3f(0f, 0f, 0f),
                    new Vector3f(0f, 0f, 0f),
                    new Vector3f(1f)
            ));
            add(new Body(
                    Material.GRAY_WOOL,
                    new Vector3f(0f, 0f, -1f),
                    new Vector3f(0f, 0f, 0f),
                    new Vector3f(1f)
            ));
            add(new Body(
                    Material.GRAY_WOOL,
                    new Vector3f(0f, 0f, -2f),
                    new Vector3f(0f, 0f, 0f),
                    new Vector3f(1f)
            ));
            add(new Body(
                    Material.GRAY_WOOL,
                    new Vector3f(0f, 0f, -3f),
                    new Vector3f(0f, 0f, 0f),
                    new Vector3f(1f)
            ));
            add(new Body(
                    Material.POLISHED_DEEPSLATE_SLAB,
                    new Vector3f(0f, 0.5f, -4f),
                    new Vector3f(0f, 0f, 0f),
                    new Vector3f(1f)
            ));




            add(new Body(
                    Material.POLISHED_DEEPSLATE_STAIRS,
                    new Vector3f(1f, 0.5f, 3f),
                    new Vector3f(90f, -90f, 0f),
                    new Vector3f(1f)
            ));
            add(new Body(
                    Material.COAL_BLOCK,
                    new Vector3f(1f, 0f, 2f),
                    new Vector3f(0f, 0f, 0f),
                    new Vector3f(1f)
            ));
            add(new Body(
                    Material.QUARTZ_BLOCK,
                    new Vector3f(1f, 0.25f, 1f),
                    new Vector3f(0f, 0f, 0f),
                    new Vector3f(0.5f)
            ));
            add(new Body(
                    Material.DARK_OAK_FENCE,
                    new Vector3f(1f, 0f, 0f),
                    new Vector3f(0f, 0f, 0f),
                    new Vector3f(1f)
            ));
            add(new Body(
                    Material.DARK_OAK_FENCE,
                    new Vector3f(1f, 0f, -1f),
                    new Vector3f(0f, 0f, 0f),
                    new Vector3f(1f)
            ));
            add(new Body(
                    Material.POLISHED_DEEPSLATE_STAIRS,
                    new Vector3f(1f, 0.5f, -2f),
                    new Vector3f(0f, 0f, 0f),
                    new Vector3f(1f)
            ));
            add(new Body(
                    Material.COAL_BLOCK,
                    new Vector3f(1f, 0f, -3f),
                    new Vector3f(0f, 0f, 0f),
                    new Vector3f(1f)
            ));
            add(new Body(
                    Material.IRON_TRAPDOOR,
                    new Vector3f(1f, 0.8f, -4f),
                    new Vector3f(0f, 0f, 0f),
                    new Vector3f(1f)
            ));



            add(new Body(
                    Material.QUARTZ_SLAB,
                    new Vector3f(0f, 0.5f, 3f),
                    new Vector3f(0f, 0f, 0f),
                    new Vector3f(1f)
            ));
            add(new Body(
                    Material.GRAY_WOOL,
                    new Vector3f(0f, 0f, 2f),
                    new Vector3f(0f, 0f, 0f),
                    new Vector3f(1f)
            ));
            add(new Body(
                    Material.GRAY_WOOL,
                    new Vector3f(0f, 0f, 1f),
                    new Vector3f(0f, 0f, 0f),
                    new Vector3f(1f)
            ));
            add(new Body(
                    Material.GRAY_WOOL,
                    new Vector3f(0f, 0f, 0f),
                    new Vector3f(0f, 0f, 0f),
                    new Vector3f(1f)
            ));
            add(new Body(
                    Material.GRAY_WOOL,
                    new Vector3f(0f, 0f, -1f),
                    new Vector3f(0f, 0f, 0f),
                    new Vector3f(1f)
            ));
            add(new Body(
                    Material.GRAY_WOOL,
                    new Vector3f(0f, 0f, -2f),
                    new Vector3f(0f, 0f, 0f),
                    new Vector3f(1f)
            ));
            add(new Body(
                    Material.GRAY_WOOL,
                    new Vector3f(0f, 0f, -3f),
                    new Vector3f(0f, 0f, 0f),
                    new Vector3f(1f)
            ));
            add(new Body(
                    Material.POLISHED_DEEPSLATE_SLAB,
                    new Vector3f(0f, 0.5f, -4f),
                    new Vector3f(0f, 0f, 0f),
                    new Vector3f(1f)
            ));


        }};
    }
}
