package marumasa.marumasa_car.vehicle;

import marumasa.marumasa_car.VehicleController;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class Vehicle extends BukkitRunnable {

    public static final Set<Interaction> SeatList = new HashSet<>();

    public static Set<Player> W = new HashSet<>();
    public static Set<Player> A = new HashSet<>();
    public static Set<Player> S = new HashSet<>();
    public static Set<Player> D = new HashSet<>();
    public static Set<Player> Jump = new HashSet<>();


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
        Location loc = body.getLocation().add(0, -0.1, 0);
        if (isSolid(loc)) {
            return inWater() ? speed / waterSubSpeed() : speed;
        } else {
            return speed / fallingSubSpeed();
        }
    }

    private final ArmorStand body;

    private final List<Entity> parts = new ArrayList<>();

    public List<Part> partsList() {
        return new ArrayList<>();
    }

    public static void createVehicle(ArmorStand stand, JavaPlugin pl) {
        final Vehicle vehicle = new Vehicle(stand);
        vehicle.runTaskTimer(pl, 0, 0);
        VehicleController.VehicleLink.put(stand, vehicle);
    }

    protected Vehicle(ArmorStand stand) {
        body = stand;
        body.setInvisible(true);
        body.setSmall(true);


        final World world = body.getWorld();
        final Location location = body.getLocation();


        for (Part part : partsList()) {
            Entity entity = world.spawnEntity(location, part.entityType);

            parts.add(entity);
            if (entity instanceof ItemDisplay display) {
                if (part.isSeat) {
                    display.setTeleportDuration(2);
                    Interaction interaction = (Interaction) world.spawnEntity(location, EntityType.INTERACTION);
                    display.addPassenger(interaction);
                    SeatList.add(interaction);
                } else {
                    display.setTeleportDuration(2);
                    display.setItemStack(new ItemStack(Material.STONE));
                }
            }
        }
    }


    public static class Part {
        public final Vector vector;
        public final EntityType entityType;

        public final boolean isSeat;

        public Part(Vector vector, EntityType entityType, boolean isSeat) {
            this.vector = vector;
            this.entityType = entityType;
            this.isSeat = isSeat;
        }

        public Part(Vector vector, EntityType entityType) {
            this(vector, entityType, false);
        }

        public Part(Vector vector, boolean isSeat) {
            this.vector = vector;
            this.entityType = EntityType.ITEM_DISPLAY;
            this.isSeat = isSeat;
        }
    }

    @Override
    public void run() {
        final Location location = body.getLocation();
        Entity mainSeat = parts.get(0);
        List<Entity> mainSeatRider = mainSeat.getPassengers().get(0).getPassengers();


        Vector vector = createVector(location);
        if (mainSeatRider.size() == 1) {
            final Location mainSeatRiderLoc = mainSeatRider.get(0).getLocation();
            final float yaw = mainSeatRiderLoc.getYaw();
            body.setRotation(yaw, 0);
            if (W.contains((Player) mainSeatRider.get(0))) {
                vector.add(new Vector(0, 0, generateSpeed(moveSpeed())).rotateAroundY(-Math.toRadians(yaw)));
            } else if (S.contains((Player) mainSeatRider.get(0))) {
                vector.add(new Vector(0, 0, -generateSpeed(backSpeed())).rotateAroundY(-Math.toRadians(yaw)));
            }
            if (A.contains((Player) mainSeatRider.get(0))) {
                vector.add(new Vector(generateSpeed(slideSpeed()), 0, 0).rotateAroundY(-Math.toRadians(yaw)));
            } else if (D.contains((Player) mainSeatRider.get(0))) {
                vector.add(new Vector(-generateSpeed(slideSpeed()), 0, 0).rotateAroundY(-Math.toRadians(yaw)));
            }
        }


        final float yaw = location.getYaw();
        for (int i = 0; i < parts.size(); i++) {
            Entity entity = parts.get(i);
            Map<Entity, Entity> passenger = removePassenger(entity);
            final Vector vec = partsList().get(i).vector;
            final Location loc = location.clone();
            loc.add(vec.clone().rotateAroundY(-Math.toRadians(yaw)));
            entity.teleport(loc);
            addPassenger(passenger);
        }
        body.setVelocity(vector);
    }

    // Map<乗っているエンティティ,乗られているエンティティ>
    private static Map<Entity, Entity> removePassenger(Entity entity) {
        final Map<Entity, Entity> passengerData = new HashMap<>();
        for (Entity ride : entity.getPassengers()) {
            entity.removePassenger(ride);
            passengerData.put(ride, entity);
        }
        return passengerData;
    }

    private static void addPassenger(Map<Entity, Entity> passengerData) {
        for (Entity key : passengerData.keySet()) {
            passengerData.get(key).addPassenger(key);
        }
    }

    private static boolean isSolid(Location loc) {
        return loc.getBlock().getType().isSolid();
    }

    private boolean inWater() {
        return body.getLocation().getBlock().getType().equals(Material.WATER);
    }

    private static boolean isOccluding(Location loc) {
        return loc.getBlock().getType().isOccluding();
    }

    private static boolean tryStep(double x, double z, Location loc) {
        boolean isOnFullBlock = loc.getY() % 1 == 0;
        if (isOnFullBlock)
            return isSolid(loc.clone().add(x, 0.9, z)) && !isSolid(loc.clone().add(x, 1, z));
        else
            return isOccluding(loc.clone().add(x, 0, z)) && !isSolid(loc.clone().add(x, 0.9, z));
    }

    private Vector createVector(Location location) {
        Location locationClone = location.clone();

        boolean isChange = false;

        float checkStep = 0.15f;

        if (tryStep(checkStep, 0, location)) {
            locationClone.add(checkStep, 0, 0);
            isChange = true;
        }
        if (tryStep(-checkStep, 0, location)) {
            locationClone.add(-checkStep, 0, 0);
            isChange = true;
        }
        if (tryStep(0, checkStep, location)) {
            locationClone.add(0, 0, checkStep);
            isChange = true;
        }
        if (tryStep(0, -checkStep, location)) {
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
