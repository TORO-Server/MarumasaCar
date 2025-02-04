package marumasa.marumasa_car.vehicle.parts.advanced;

import marumasa.marumasa_car.vehicle.Vehicle;
import marumasa.marumasa_car.vehicle.VehicleController;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class AdvancedVehicle extends Vehicle {
    public AdvancedVehicle advancedVehicleInstance;

    public float moveSpeedF = 0.4f;
    public float backSpeedF = 0.2f;
    public float fallingSubSpeedF = 4f;
    public float waterSubSpeedF = 3f;
    public float jumpPowerF = 0.5f;
    public float waterSubJumpF = 6f;

    @Override
    public float move_Speed() {return moveSpeedF;}
    @Override
    public float backSpeed() {return backSpeedF;}
    @Override
    public float fallingSubSpeed() {return fallingSubSpeedF;}
    @Override
    public float waterSubSpeed() {return waterSubSpeedF;}
    @Override
    public float jumpPower() {return jumpPowerF;}
    @Override
    public float waterSubJump() {return waterSubJumpF;}

    public AdvancedVehicle(ArmorStand stand, JavaPlugin pl) {
        super(stand, pl);
    }

    @Override
    public void onBeforeGenerateParts() {
        advancedVehicleInstance = this;
    }

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

            if (VehicleController.W.contains(driver)) {
                addVector(0, 0, generateSpeed(move_Speed()), location.getYaw());
            } else if (VehicleController.S.contains(driver)) {
                addVector(0, 0, -generateSpeed(backSpeed()), location.getYaw());
            }

            if (VehicleController.Jump.contains(driver)) {
                addVector(0, generateJump(), 0, location.getYaw());
            }
        }

        final float yaw = location.getYaw();

        for (Entity entity : partsMap.keySet()) partsMap.get(entity).tick(location, yaw, entity);

        body.setVelocity(vector);
    }
}
