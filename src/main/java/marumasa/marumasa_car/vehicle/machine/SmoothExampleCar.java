package marumasa.marumasa_car.vehicle.machine;

import marumasa.marumasa_car.vehicle.Vehicle;
import marumasa.marumasa_car.vehicle.VehicleController;
import marumasa.marumasa_car.vehicle.parts.Body;
import marumasa.marumasa_car.vehicle.parts.MainSeat;
import marumasa.marumasa_car.vehicle.parts.Part;
import marumasa.marumasa_car.vehicle.parts.Seat;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class SmoothExampleCar extends Vehicle {
    public SmoothExampleCar(ArmorStand stand, JavaPlugin pl) {
        super(stand, pl);
    }

    @Override
    public List<Part> generateParts() {
        return new ArrayList<>() {{
            add(new MainSeat(new Vector(0, 0.5, 0.48), 0.8f, 1));
            add(new Seat(new Vector(0, 0.5, -0.48), 0.8f, 1));
            add(new Body(
                    Material.DISC_FRAGMENT_5,
                    new Vector3f(0f, 0f, 0f),
                    new Vector3f(5f),
                    1
            ));
        }};
    }

    public float move_tick = 0;
    public final float move_tick_max = 100;

    private double last_speed = 0;

    @Override
    public void run() {
        location = body.getLocation();
        vector = body.getVelocity();

        // 自動で1ブロックの段差を登れるようにする
        autoStep();

        final Player driver = getDriver();

        if (driver != null) {
            final double now_speed = vector.clone().multiply(new Vector(1, 0, 1)).length();

            final float rotation_speed = (float) (now_speed * 20f);

            if (VehicleController.A.contains(driver)) {
                body.setRotation(location.getYaw() - rotation_speed, location.getPitch());
            } else if (VehicleController.D.contains(driver)) {
                body.setRotation(location.getYaw() + rotation_speed, location.getPitch());
            }

            location = body.getLocation();

            if (last_speed > now_speed && now_speed < 0.01) move_tick = 0;

            if (VehicleController.W.contains(driver)) {
                move_tick += move_tick < move_tick_max ? 2 : 0;
            } else if (VehicleController.S.contains(driver)) {
                move_tick -= move_tick > -move_tick_max ? 2 : 0;
            } else {
                move_tick -= Math.signum(move_tick);
            }
            // アークタンジェントを利用して滑らかに加速や減速させる
            float smooth_speed = (float) Math.min(Math.atan((double) move_tick / move_tick_max) * move_Speed(), move_Speed());
            addVector(0, 0, generateSpeed(smooth_speed), location.getYaw());

            if (VehicleController.Jump.contains(driver)) {
                addVector(0, generateJump(), 0, location.getYaw());
            }
            last_speed = now_speed;
        }

        final float yaw = location.getYaw();

        for (Entity entity : partsMap.keySet()) partsMap.get(entity).tick(location, yaw, entity);

        body.setVelocity(vector);
    }
}