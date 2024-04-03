package marumasa.marumasa_car.vehicle;

import marumasa.marumasa_car.vehicle.parts.Part;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static marumasa.marumasa_car.vehicle.VehicleUtils.isOccluding;
import static marumasa.marumasa_car.vehicle.VehicleUtils.isSolid;

public class Vehicle extends BukkitRunnable {
    public float move_Speed() {
        return 0.4f;
    }

    public float rotation_speed() {
        return 0.1f;
    }

    public float backSpeed() {
        return 0.2f;
    }

    public float fallingSubSpeed() {
        return 4f;
    }

    public float waterSubSpeed() {
        return 3f;
    }

    public float jumpPower() {
        return 0.5f;
    }

    public float waterSubJump() {
        return 6f;
    }

    private double generateSpeed(float speed) {
        Location loc = location.clone().add(0, -0.1, 0);
        if (isSolid(loc)) {
            return inWater() ? speed / waterSubSpeed() : speed;
        } else {
            return speed / fallingSubSpeed();
        }
    }

    private double generateJump() {
        Location loc = location.clone().add(0, -0.1, 0);
        if (isSolid(loc)) {
            return jumpPower();
        } else {
            return inWater() ? jumpPower() / waterSubJump() : 0;
        }
    }

    public final ArmorStand body;
    public Interaction mainSeat;

    // 乗り物に追従するエンティティリスト
    public final List<Entity> EntityListTracking = new ArrayList<>();
    // すべてのエンティティリスト
    public final List<Entity> EntityListAll = new ArrayList<>();

    private final Map<Entity, Part> partsMap = new HashMap<>();

    public List<Part> generateParts() {
        return new ArrayList<>();
    }

    private Location location;
    private Vector vector;

    public Vehicle(ArmorStand stand, JavaPlugin pl) {

        this.runTaskTimer(pl, 0, 0);
        VehicleController.VehicleLink.put(stand, this);

        body = stand;
        body.setInvisible(true);
        body.setSmall(true);

        location = body.getLocation();

        List<Part> parts = generateParts();

        final World world = body.getWorld();

        for (Part part : parts) partsMap.put(part.create(world, location, this), part);

    }

    // メインシートにプレイヤーが乗っていた場合、プレイヤーを return する
    // そうでなかったら null にする
    public Player getDriver() {
        if (mainSeat == null) return null;
        final List<Entity> passengers = mainSeat.getPassengers();
        if (passengers.size() != 1) return null;
        return passengers.get(0) instanceof Player driver ? driver : null;
    }

    public void addVector(double x, double y, double z, float yaw) {
        vector.add(new Vector(x, y, z).rotateAroundY(-Math.toRadians(yaw)));
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

    private boolean inWater() {
        return location.getBlock().getType().equals(Material.WATER);
    }


    private boolean tryStep(double x, double z) {
        boolean isOnFullBlock = location.getY() % 1 == 0;
        if (isOnFullBlock)
            return isSolid(location.clone().add(x, 0.9, z)) && !isSolid(location.clone().add(x, 1, z));
        else
            return isOccluding(location.clone().add(x, 0, z)) && !isSolid(location.clone().add(x, 0.9, z));
    }

    private void autoStep() {
        Location locationClone = location.clone();

        boolean isChange = false;

        float checkStep = 0.15f;

        if (tryStep(checkStep, 0)) {
            locationClone.add(checkStep, 0, 0);
            isChange = true;
        }
        if (tryStep(-checkStep, 0)) {
            locationClone.add(-checkStep, 0, 0);
            isChange = true;
        }
        if (tryStep(0, checkStep)) {
            locationClone.add(0, 0, checkStep);
            isChange = true;
        }
        if (tryStep(0, -checkStep)) {
            locationClone.add(0, 0, -checkStep);
            isChange = true;
        }
        if (isChange) {
            body.teleport(locationClone.add(0, 1, 0));
        }
    }

    public void unload() {
        this.cancel();
        for (Entity entity : EntityListAll) entity.remove();
    }

    public void remove() {
        body.remove();
    }
}
