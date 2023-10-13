package marumasa.marumasa_car.vehicle;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class VehicleParts {
    public static class SeatPart extends TrackingPart {
        public SeatPart(Vector vector) {
            super(vector, EntityType.ITEM_DISPLAY);
        }

        @Override
        public Entity create(World world, Location location, Vehicle vehicle) {
            final ItemDisplay itemDisplay = (ItemDisplay) super.create(world, location, vehicle);

            itemDisplay.setTeleportDuration(2);

            Interaction interaction = (Interaction) new Part(
                    vector,
                    EntityType.INTERACTION
            ).create(world, location, vehicle);

            itemDisplay.addPassenger(interaction);
            VehicleController.SeatList.add(interaction);

            return itemDisplay;
        }
    }

    public static class BodyPart extends TrackingPart {
        public final Material material;

        public BodyPart(Vector vector, Material material) {
            super(vector, EntityType.ITEM_DISPLAY);
            this.material = material;
        }

        @Override
        public Entity create(World world, Location location, Vehicle vehicle) {
            final ItemDisplay itemDisplay = (ItemDisplay) super.create(world, location, vehicle);

            ItemStack itemStack = new ItemStack(material);

            itemDisplay.setItemStack(itemStack);

            return itemDisplay;
        }
    }

    public static class TrackingPart extends Part {
        public TrackingPart(Vector vector, EntityType entityType) {
            super(vector, entityType);
        }

        @Override
        public Entity create(World world, Location location, Vehicle vehicle) {
            final Entity entity = super.create(world, location, vehicle);
            vehicle.EntityListTracking.add(entity);
            return entity;
        }
    }

    public static class Part {
        public final Vector vector;
        public final EntityType entityType;

        public Part(Vector vector, EntityType entityType) {
            this.vector = vector;
            this.entityType = entityType;
        }

        public Entity create(World world, Location location, Vehicle vehicle) {
            final Entity entity = world.spawnEntity(location, entityType);
            vehicle.EntityListAll.add(entity);
            return entity;
        }
    }
}
