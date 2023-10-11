package marumasa.marumasa_car;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Car extends BukkitRunnable {
    public final ArmorStand body;
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

        Vector vector = createVector(location);
        if (mainSeatRider.size() == 1) {
            final Location mainSeatRiderLoc = mainSeatRider.get(0).getLocation();
            final float yaw = mainSeatRiderLoc.getYaw();
            body.setRotation(yaw, 0);
            vector.add(new Vector(0, 0, 1.5).rotateAroundY(-Math.toRadians(yaw)));
        }
        body.setVelocity(vector);

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

    private static Vector createVector(Location location) {

        if (location.clone().add(0, -1, 0).getBlock().getType().isSolid())
            return new Vector(0, 0.2, 0);

        else if (location.clone().add(0, -1.3, 0).getBlock().getType().isAir())
            return new Vector(0, -0.5, 0);

        return new Vector(0, 0, 0);
    }

    public void remove() {
        this.cancel();
    }
}
