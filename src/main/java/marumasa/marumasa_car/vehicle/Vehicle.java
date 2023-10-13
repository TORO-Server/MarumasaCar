package marumasa.marumasa_car.vehicle;

import marumasa.marumasa_car.vehicle.parts.Part;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static marumasa.marumasa_car.vehicle.VehicleUtils.*;

public class Vehicle extends BukkitRunnable {
    public float moveSpeed() {
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

    private double generateSpeed(float speed) {
        Location loc = location.clone().add(0, -0.1, 0);
        if (isSolid(loc)) {
            return inWater() ? speed / waterSubSpeed() : speed;
        } else {
            return speed / fallingSubSpeed();
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


    @Override
    public void run() {
        location = body.getLocation();

        Entity mainSeat = EntityListTracking.get(0);
        List<Entity> mainSeatRider = mainSeat.getPassengers().get(0).getPassengers();


        Vector vector = createVector();
        if (mainSeatRider.size() == 1) {
            final Location mainSeatRiderLoc = mainSeatRider.get(0).getLocation();
            final float yaw = mainSeatRiderLoc.getYaw();
            body.setRotation(yaw, 0);
            if (VehicleController.W.contains((Player) mainSeatRider.get(0))) {
                vector.add(new Vector(0, 0, generateSpeed(moveSpeed())).rotateAroundY(-Math.toRadians(yaw)));
            } else if (VehicleController.S.contains((Player) mainSeatRider.get(0))) {
                vector.add(new Vector(0, 0, -generateSpeed(backSpeed())).rotateAroundY(-Math.toRadians(yaw)));
            }
            if (VehicleController.A.contains((Player) mainSeatRider.get(0))) {
                vector.add(new Vector(generateSpeed(slideSpeed()), 0, 0).rotateAroundY(-Math.toRadians(yaw)));
            } else if (VehicleController.D.contains((Player) mainSeatRider.get(0))) {
                vector.add(new Vector(-generateSpeed(slideSpeed()), 0, 0).rotateAroundY(-Math.toRadians(yaw)));
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

    private Vector createVector() {
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
        return body.getVelocity();
    }

    public void remove() {
        this.cancel();
    }
}
