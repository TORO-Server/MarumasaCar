package marumasa.marumasa_car.vehicle;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

import static marumasa.marumasa_car.vehicle.VehicleController.LoadingParts;

public class VehicleEventManager {

    public void onUse(Entity entity, Player player) {
        if (player.isSneaking())
            remove(entity);
        else
            ride(entity, player);
    }


    public void load(ArmorStand stand, Set<String> tags) {
        Machine.tryLoad(tags, stand, pl);
    }


    private final JavaPlugin pl;

    public VehicleEventManager(JavaPlugin plugin) {
        pl = plugin;

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(pl, ListenerPriority.HIGHEST, PacketType.Play.Client.STEER_VEHICLE) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                Player player = event.getPlayer();

                Entity b = player.getVehicle();
                if (b instanceof Player) {
                    return;
                }

                if (packet.getFloat().read(1) > 0) {
                    VehicleController.W.add(player);
                } else if (packet.getFloat().read(1) < 0) {
                    VehicleController.S.add(player);
                } else {
                    VehicleController.W.remove(player);
                    VehicleController.S.remove(player);
                }

                if (packet.getFloat().read(0) > 0) {
                    VehicleController.A.add(player);
                } else if (packet.getFloat().read(0) < 0) {
                    VehicleController.D.add(player);
                } else {
                    VehicleController.A.remove(player);
                    VehicleController.D.remove(player);
                }

                if (packet.getBooleans().read(0)) {
                    VehicleController.Jump.add(player);
                } else {
                    VehicleController.Jump.remove(player);
                }
            }
        });
    }

    public void load(Entity entity) {
        new BukkitRunnable() {
            @Override
            public void run() {

                if (LoadingParts.contains(entity)) {
                    LoadingParts.remove(entity);
                    return;
                }

                final Set<String> tags = entity.getScoreboardTags();

                if (VehicleUtils.isVehicleParts(tags)) {
                    entity.remove();
                    return;
                }
                if (entity instanceof ArmorStand stand) {
                    load(stand, tags);
                }
            }
        }.runTaskLater(pl, 1);
    }

    public void unload(Entity entity) {
        if (entity instanceof ArmorStand stand) unload(stand);
    }

    public Vehicle unload(ArmorStand stand) {
        Vehicle vehicle = VehicleController.VehicleLink.remove(stand);
        if (vehicle == null) return null;
        vehicle.unload();
        return vehicle;
    }

    public void remove(Entity entity) {
        if (entity instanceof Interaction interaction) {
            ArmorStand body = VehicleController.InteractionLink.remove(interaction);
            if (body == null) return;
            final Vehicle vehicle = unload(body);
            vehicle.remove();
        }
    }

    public void ride(Entity entity, Player player) {
        if (entity instanceof Interaction interaction) {
            if (interaction.getPassengers().size() != 0 || !VehicleController.SeatList.contains(interaction)) return;
            interaction.addPassenger(player);
        }
    }
}
