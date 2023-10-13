package marumasa.marumasa_car.vehicle.parts;

import marumasa.marumasa_car.vehicle.Vehicle;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.Vector3f;

public class Body extends TrackingDisplay {
    public final Material material;

    // null の場合もある
    public final Integer customModelData;

    public final Vector3f translation;
    public final Vector3f scale;

    public Body(Material material, Vector3f translation, Vector3f scale, Integer customModelData) {
        this(new Vector(0f, 0.7f, 0f), material, translation.add(0f, scale.y / 2 - 0.7f, 0f), scale, customModelData);
    }

    public Body(Vector vector, Material material, Vector3f translation, Vector3f scale, Integer customModelData) {
        super(vector, EntityType.ITEM_DISPLAY);
        this.material = material;

        this.translation = translation;
        this.scale = scale;

        this.customModelData = customModelData;
    }

    public Transformation generateTransformation(Display display) {
        final Transformation tra = display.getTransformation();
        return new Transformation(
                translation,
                tra.getLeftRotation(),
                scale,
                tra.getRightRotation()
        );
    }

    @Override
    public ItemDisplay create(World world, Location location, Vehicle vehicle) {
        final ItemDisplay itemDisplay = (ItemDisplay) super.create(world, location, vehicle);

        itemDisplay.setTransformation(
                generateTransformation(itemDisplay)
        );

        final ItemStack itemStack = new ItemStack(material);
        final ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            itemMeta.setCustomModelData(customModelData);
            itemStack.setItemMeta(itemMeta);
        }

        itemDisplay.setItemStack(itemStack);
        return itemDisplay;
    }
}
