package marumasa.marumasa_car.vehicle.parts;

import marumasa.marumasa_car.vehicle.Vehicle;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Body extends Tracking {
    public final Material material;

    public Body(Vector vector, Material material) {
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
