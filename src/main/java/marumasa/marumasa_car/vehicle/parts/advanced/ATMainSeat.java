package marumasa.marumasa_car.vehicle.parts.advanced;

import marumasa.marumasa_car.vehicle.Vehicle;
import marumasa.marumasa_car.vehicle.advanced.AdvancedVehicle;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

public class ATMainSeat extends AdvancedMainSeat {
    protected final ItemStack[] shiftGearHotbar = {
            createItemStack(Material.RED_STAINED_GLASS_PANE, 1, "P - パーキング",
                    "トランスミッションにロックがかかり、車が動かなくなります。"),
            createItemStack(Material.ORANGE_STAINED_GLASS_PANE, 1, "R - リバース",
                    "バックギアです。後退する時に使います。"),
            createItemStack(Material.LIME_STAINED_GLASS_PANE, 1, "N - ニュートラル",
                    "エンジンを切り離します。完全に停止しないので注意。"),
            createItemStack(Material.BLUE_STAINED_GLASS_PANE, 1, "D - ドライブ",
                    "通常走行のギアです。自動でトランスミッションを変更します。"),
            createItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1, "2 - セカンド",
                    "2速に固定します。そこそこパワーが必要な時。"),
            createItemStack(Material.PINK_STAINED_GLASS_PANE, 1, "L - ロー",
                    "1速に固定します。2速でもパワーが足りない時。"),
            null,
            null,
            null,
    };

    protected final ItemStack[] functionInventory = {
            createItemStack(Material.YELLOW_CONCRETE, 1, "ハザード/ウィンカー"),
            createItemStack(Material.ORANGE_CONCRETE, 1, "クラクション"),
            null,null,null,null,null,null,
            createItemStack(Material.RED_CONCRETE, 1, "車を削除"),
            null,null,null,null,null,null,null,null,null,
            null,null,null,null,null,null,null,null,null,
    };

    public ATMainSeat(Vector vector, float width, float height, AdvancedVehicle vehicle) {
        super(vector, width, height, vehicle);
    }

    @Override
    protected ItemStack[] getAdvancedHotbar() {return shiftGearHotbar;}
    @Override
    protected ItemStack[] getAdvancedInventory() {return functionInventory;}

    @Override
    public Display create(World world, Location location, @Nonnull Vehicle vehicle) {
        return super.create(world, location, vehicle);
    }

    @Override
    public void tick(Location location, float yaw, Entity entity) {
        super.tick(location, yaw, entity);
    }

    @Override
    public void onSwitchGear(@Nonnegative int slot) {
        super.onSwitchGear(slot);

        switch (slot) {
            case 0: case 2:
                vehicle.backSpeedF = 0F;
                vehicle.moveSpeedF = 0F;
                vehicle.jumpPowerF = 0.5F;
                break;
            case 1:
                vehicle.backSpeedF = -0F;
                vehicle.moveSpeedF = -0.2F;
                vehicle.jumpPowerF = 0.5F;
                break;
            case 3:
                vehicle.backSpeedF = 0F;
                vehicle.moveSpeedF = 0.4F;
                vehicle.jumpPowerF = 0.5F;
                break;
            case 4:
                vehicle.backSpeedF = 0F;
                vehicle.moveSpeedF = 0.2F;
                vehicle.jumpPowerF = 0.75F;
                break;
            case 5:
                vehicle.backSpeedF = 0F;
                vehicle.moveSpeedF = 0.1F;
                vehicle.jumpPowerF = 1F;
                break;
        }
    }

    @Override
    public void functionInventoryHandler(@Nonnegative int slot) {
        switch (slot) {
            case 0:
                vehicle.brinker = !vehicle.brinker;
                break;
            case 1:
                vehicle.playHorn();
                break;
            case 8:
                vehicle.unload();
                break;
        }
    }
}
