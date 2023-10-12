package marumasa.marumasa_car.vehicle;

import marumasa.marumasa_car.MarumasaCar;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class Car extends BukkitRunnable {

    public static Set<Player> W = new HashSet<>();
    public static Set<Player> A = new HashSet<>();
    public static Set<Player> S = new HashSet<>();
    public static Set<Player> D = new HashSet<>();
    public static Set<Player> Jump = new HashSet<>();

    public final ArmorStand body;
    /*public final ArmorStand HitBox_X_Plus;
    public final ArmorStand HitBox_X_Minus;
    public final ArmorStand HitBox_Y_Plus;
    public final ArmorStand HitBox_Y_Minus;
    public final ArmorStand HitBox_Z_Plus;
    public final ArmorStand HitBox_Z_Minus;*/

    public final List<Entity> parts = new ArrayList<>();

    public List<Part> partsList() {
        return new ArrayList<>();
    }

    public Car(ArmorStand armorStand) {
        body = armorStand;
        body.setInvisible(true);
        body.setSmall(true);


        final World world = armorStand.getWorld();
        final Location location = armorStand.getLocation();

        /*HitBox_X_Plus = (ArmorStand) world.spawnEntity(location, EntityType.ARMOR_STAND);
        HitBox_X_Minus = (ArmorStand) world.spawnEntity(location, EntityType.ARMOR_STAND);
        HitBox_Y_Plus = (ArmorStand) world.spawnEntity(location, EntityType.ARMOR_STAND);
        HitBox_Y_Minus = (ArmorStand) world.spawnEntity(location, EntityType.ARMOR_STAND);
        HitBox_Z_Plus = (ArmorStand) world.spawnEntity(location, EntityType.ARMOR_STAND);
        HitBox_Z_Minus = (ArmorStand) world.spawnEntity(location, EntityType.ARMOR_STAND);*/

        for (Part part : partsList()) {
            Entity entity = world.spawnEntity(location, part.entityType);

            parts.add(entity);
            if (entity instanceof ItemDisplay display) {
                if (part.isSeat) {
                    display.setTeleportDuration(2);
                    Interaction interaction = (Interaction) world.spawnEntity(location, EntityType.INTERACTION);
                    display.addPassenger(interaction);
                    MarumasaCar.SeatLink.put(interaction, interaction);
                } else {
                    display.setTeleportDuration(2);
                    display.setItemStack(new ItemStack(Material.STONE));
                }
            }
        }
    }


    public static class Part {
        public Vector vector;
        public EntityType entityType;

        public boolean isSeat;

        public Part(Vector vector, EntityType entityType, boolean isSeat) {
            this.vector = vector;
            this.entityType = entityType;
            this.isSeat = isSeat;
        }
    }

    @Override
    public void run() {
        final Location location = body.getLocation();
        Entity mainSeat = parts.get(0);
        List<Entity> mainSeatRider = mainSeat.getPassengers().get(0).getPassengers();

        final double speed = isSolid(location.clone().add(0, -0.1, 0)) ? 0.5 : 0.2;

        Vector vector = createVector(location);
        if (mainSeatRider.size() == 1) {
            final Location mainSeatRiderLoc = mainSeatRider.get(0).getLocation();
            final float yaw = mainSeatRiderLoc.getYaw();
            body.setRotation(yaw, 0);
            if (W.contains((Player) mainSeatRider.get(0))) {
                vector.add(new Vector(0, 0, speed).rotateAroundY(-Math.toRadians(yaw)));
            } else if (S.contains((Player) mainSeatRider.get(0))) {
                vector.add(new Vector(0, 0, -speed).rotateAroundY(-Math.toRadians(yaw)));
            }
            if (A.contains((Player) mainSeatRider.get(0))) {
                vector.add(new Vector(speed, 0, 0).rotateAroundY(-Math.toRadians(yaw)));
            } else if (D.contains((Player) mainSeatRider.get(0))) {
                vector.add(new Vector(-speed, 0, 0).rotateAroundY(-Math.toRadians(yaw)));
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


        /*HitBox_X_Plus.teleport(location.clone().add(2, 0, 0));
        HitBox_X_Minus.teleport(location.clone().add(-2, 0, 0));
        HitBox_Y_Plus.teleport(location.clone().add(0, 2, 0));
        HitBox_Y_Minus.teleport(location.clone().add(0, -2, 0));
        HitBox_Z_Plus.teleport(location.clone().add(0, 0, 2));
        HitBox_Z_Minus.teleport(location.clone().add(0, 0, -2));*/

        /*if (location.clone().add(2, 0, 0).getBlock().getType().isSolid()) {
            if (vector.getX() > 0) vector.setX(0);
        }
        if (location.clone().add(-2, 0, 0).getBlock().getType().isSolid()) {
            if (vector.getX() < 0) vector.setX(0);
        }
        if (location.clone().add(0, 0, 2).getBlock().getType().isSolid()) {
            if (vector.getZ() > 0) vector.setZ(0);
        }
        if (location.clone().add(0, 0, -2).getBlock().getType().isSolid()) {
            if (vector.getZ() < 0) vector.setZ(0);
        }*/
        body.setVelocity(vector);
    }

    // Map<乗っているエンティティ,乗られているエンティティ>
    public static Map<Entity, Entity> removePassenger(Entity entity) {
        final Map<Entity, Entity> passengerData = new HashMap<>();
        for (Entity ride : entity.getPassengers()) {
            entity.removePassenger(ride);
            passengerData.put(ride, entity);
        }
        return passengerData;
    }

    public static void addPassenger(Map<Entity, Entity> passengerData) {
        for (Entity key : passengerData.keySet()) {
            passengerData.get(key).addPassenger(key);
        }
    }

    private static boolean isSolid(Location loc) {
        return loc.getBlock().getType().isSolid();
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
