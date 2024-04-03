package marumasa.marumasa_car.vehicle.parts;

import marumasa.marumasa_car.vehicle.Vehicle;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Body extends TrackingDisplay {
    public final Material material;

    // null の場合もある
    public final Integer customModelData;

    public final Vector3f translation;
    public final Quaternionf rotation;
    public final Vector3f scale;

    public Body(Material material, Vector3f translation, Quaternionf rotation, Vector3f scale, Integer customModelData) {
        this(new Vector(0f, 0.7f, 0f), material, translation.add(0f, scale.y / 2 - 0.7f, 0f), rotation, scale, customModelData);
    }

    public Body(Material material, Vector3f translation, Vector3f rotation, Vector3f scale) {
        this(
                new Vector(0f, 0.7f, 0f),
                material,
                translation.add(0f, scale.y / 2 - 0.7f, 0f),
                new Quaternionf().rotationX(rotation.x).rotationY(rotation.y).rotationZ(rotation.z),
                scale
        );
    }

    public Body(Vector vector, Material material, Vector3f translation, Quaternionf rotation, Vector3f scale) {
        this(vector, material, translation, rotation, scale, null);
    }

    public Body(Vector vector, Material material, Vector3f translation, Quaternionf rotation, Vector3f scale, Integer customModelData) {
        super(vector, EntityType.ITEM_DISPLAY);
        this.material = material;

        this.translation = translation;
        this.rotation = rotation;
        this.scale = scale;

        this.customModelData = customModelData;
    }

    public Transformation generateTransformation() {
        return new Transformation(translation, scale,new Quaternionf());
    }

    @Override
    public ItemDisplay create(World world, Location location, Vehicle vehicle) {
        final ItemDisplay itemDisplay = (ItemDisplay) super.create(world, location, vehicle);

        itemDisplay.setTransformation(generateTransformation());

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
