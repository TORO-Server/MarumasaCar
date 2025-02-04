package marumasa.marumasa_car.vehicle.parts.advanced;

import marumasa.marumasa_car.vehicle.Vehicle;
import marumasa.marumasa_car.vehicle.parts.MainSeat;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

abstract class AdvancedMainSeat extends MainSeat {
    private static final int HOTBAR_SIZE = 9;

    @Nonnull
    protected AdvancedVehicle vehicle;
    protected boolean isPassenger = false;
    protected final ItemStack[] itemStacks = new ItemStack[HOTBAR_SIZE];
    protected Player lastPlayer = null;
    @Nonnegative
    protected int lastHand = -1;

    public AdvancedMainSeat(Vector vector, float width, float height, @Nonnull AdvancedVehicle vehicle) {
        super(vector, width, height);
        this.vehicle = vehicle;
    }

    abstract protected ItemStack[] getAdvancedHotbar();
    protected ItemStack createItemStack(@Nonnull Material material, @Nullable @Nonnegative Integer amount,
                                        @Nullable String displayName, @Nullable String... lore) {
        final ItemStack itemStack;
        if (amount == null) itemStack = new ItemStack(material);
        else itemStack = new ItemStack(material, amount);
        var meta = itemStack.getItemMeta();
        if (meta == null) return itemStack;
        meta.setDisplayName(displayName);
        meta.setLore(lore == null ? List.of() : Arrays.asList(lore));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public Display create(World world, Location location, Vehicle vehicle) {
        return super.create(world, location, vehicle);
    }

    @Override
    public void tick(Location location, float yaw, Entity entity) {
        super.tick(location, yaw, entity);
        var passenger = vehicle.getDriver();

        if (passenger == null && isPassenger) {
            for (int i = 0; i < HOTBAR_SIZE; i++) lastPlayer.getInventory().setItem(i, itemStacks[i]);
            isPassenger = false;
        } else if (passenger != null && !isPassenger) {
            lastPlayer = passenger;
            for (int i = 0; i < HOTBAR_SIZE; i++) itemStacks[i] = passenger.getInventory().getItem(i);
            isPassenger = true;
        }

        if (passenger != null) {
            for (int i = 0; i < HOTBAR_SIZE; i++)
                lastPlayer.getInventory().setItem(i, getAdvancedHotbar()[i] == null ? new ItemStack(Material.AIR) :
                        getAdvancedHotbar()[i]);

            for (int i = 0; i < HOTBAR_SIZE; i++) {
                var itemStack = passenger.getInventory().getItem(i);
                if (itemStack != null && itemStack.getType() == passenger.getInventory().getItemInMainHand().getType())
                {
                    if (lastHand != i) onSwitchGear(i);
                    lastHand = i;
                    break;
                }
            }
        }
    }

    public void onSwitchGear(@Nonnegative int slot) {
        var world = vehicle.mainSeat.getLocation().getWorld();
        if (world != null) world.playSound(vehicle.mainSeat.getLocation(), Sound.BLOCK_STONE_BREAK, 1, 0.5f);
    }
}
