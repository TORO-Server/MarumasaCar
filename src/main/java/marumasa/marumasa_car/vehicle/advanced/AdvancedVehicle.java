package marumasa.marumasa_car.vehicle.advanced;

import marumasa.marumasa_car.vehicle.Vehicle;
import marumasa.marumasa_car.vehicle.VehicleController;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class AdvancedVehicle extends Vehicle {
    public double addSpeed = 0;
    public double savedSpeed = 0;

    public int tick20 = 0;
    public AdvancedVehicle advancedVehicleInstance;
    public boolean brinker = false;

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

            final float rotation_speed = (float) (now_speed * 5f);

            if (VehicleController.A.contains(driver)) {
                body.setRotation(location.getYaw() - rotation_speed, location.getPitch());
            } else if (VehicleController.D.contains(driver)) {
                body.setRotation(location.getYaw() + rotation_speed, location.getPitch());
            }

            location = body.getLocation();

            if (VehicleController.W.contains(driver)) {
                addSpeed += (generateSpeed(move_Speed()) - addSpeed) / 128;
            } else if (VehicleController.S.contains(driver)) {
                addSpeed += (-generateSpeed(backSpeed()) - addSpeed) / 12;
            }

            if (VehicleController.Jump.contains(driver)) {
                addVector(0, generateJump(), 0, location.getYaw());
            }
        }

        savedSpeed += addSpeed;
        addVector(0, 0, savedSpeed, location.getYaw());
        addSpeed += -addSpeed / 12;
        savedSpeed += -savedSpeed / 24;

        final float yaw = location.getYaw();

        for (Entity entity : partsMap.keySet()) partsMap.get(entity).tick(location, yaw, entity);

        body.setVelocity(vector);

        tick20++;
        var world = location.getWorld();
        if (world != null) if (tick20 > 19) {
            tick20 = 0;
            if (brinker) world.playSound(location, Sound.BLOCK_NOTE_BLOCK_HAT, 1, 1);
        } else if (tick20 == 9 && brinker) world.playSound(location, Sound.BLOCK_NOTE_BLOCK_HAT, 1, 1.5f);
    }

    public void playHorn() {
        var world = location.getWorld();
        if (world != null) world.playSound(location, Sound.ENTITY_ENDERMITE_HURT, 3, 1);
    }
}
