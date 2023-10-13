package marumasa.marumasa_car.vehicle;

import marumasa.marumasa_car.vehicle.parts.Part;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static marumasa.marumasa_car.vehicle.VehicleUtils.*;

public class Vehicle extends BukkitRunnable {
    public float frontSpeed() {
        return 0.4f;
    }

    public float slideSpeed() {
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

    public List<Part> partsList() {
        return new ArrayList<>();
    }

    private Location location;
    private Vector vector;


    /*public void dropVehicleItem(String tag) {

    }*/


    public Vehicle(ArmorStand stand, JavaPlugin pl) {

        this.runTaskTimer(pl, 0, 0);
        VehicleController.VehicleLink.put(stand, this);

        body = stand;
        body.setInvisible(true);
        body.setSmall(true);

        location = body.getLocation();

        final World world = body.getWorld();
        for (Part part : partsList()) {
            part.create(world, location, this);
        }
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
            final float yaw = driver.getLocation().getYaw();
            body.setRotation(yaw, 0);

            if (VehicleController.W.contains(driver)) {
                addVector(0, 0, generateSpeed(frontSpeed()), yaw);
            } else if (VehicleController.S.contains(driver)) {
                addVector(0, 0, -generateSpeed(backSpeed()), yaw);
            }

            if (VehicleController.A.contains(driver)) {
                addVector(generateSpeed(slideSpeed()), 0, 0, yaw);
            } else if (VehicleController.D.contains(driver)) {
                addVector(-generateSpeed(slideSpeed()), 0, 0, yaw);
            }

            if (VehicleController.Jump.contains(driver)) {
                addVector(0, generateJump(), 0, yaw);
            }
        }


        final float yaw = location.getYaw();
        for (int i = 0; i < EntityListTracking.size(); i++) {
            Entity entity = EntityListTracking.get(i);
            Map<Entity, Entity> passenger = removePassenger(entity);
            final Vector vec = partsList().get(i).vector;
            final Location loc = location.clone();
            loc.add(vec.clone().rotateAroundY(-Math.toRadians(yaw)));
            entity.teleport(loc);
            addPassenger(passenger);
        }
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
