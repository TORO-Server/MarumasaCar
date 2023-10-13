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

import java.util.List;

public class VehicleParts {
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

        public void create(World world, Location location, List<Entity> trackingEntities) {

            Entity entity = world.spawnEntity(location, entityType);

            trackingEntities.add(entity);

            if (entity instanceof ItemDisplay display) {
                if (isSeat) {
                    display.setTeleportDuration(2);
                    Interaction interaction = (Interaction) world.spawnEntity(location, EntityType.INTERACTION);
                    display.addPassenger(interaction);
                    VehicleController.SeatList.add(interaction);
                } else {
                    display.setTeleportDuration(2);
                    display.setItemStack(new ItemStack(Material.STONE));
                }
            }
        }
    }
}
