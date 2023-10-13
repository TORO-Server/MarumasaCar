package marumasa.marumasa_car.vehicle.parts;

import marumasa.marumasa_car.vehicle.Vehicle;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class Body extends TrackingDisplay {
    public final Material material;

    public final Integer CustomModelData;

    public Body(Vector vector, Material material) {
        super(vector, EntityType.ITEM_DISPLAY);
        this.material = material;
        this.CustomModelData = null;
    }

    public Body(Vector vector, Material material, int CustomModelData) {
        super(vector, EntityType.ITEM_DISPLAY);
        this.material = material;
        this.CustomModelData = CustomModelData;
    }

    @Override
    public ItemDisplay create(World world, Location location, Vehicle vehicle) {
        final ItemDisplay itemDisplay = (ItemDisplay) super.create(world, location, vehicle);

        final ItemStack itemStack = new ItemStack(material);
        final ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            itemMeta.setCustomModelData(CustomModelData);
            itemStack.setItemMeta(itemMeta);
        }

        itemDisplay.setItemStack(itemStack);
        return itemDisplay;
    }
}
