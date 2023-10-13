package marumasa.marumasa_car.vehicle;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import marumasa.marumasa_car.vehicle.machine.TestCar;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public class VehicleEventManager {
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
        final Set<String> tags = entity.getScoreboardTags();
        if (entity instanceof ArmorStand stand) {
            if (tags.contains("marumasa.test")) {
                new TestCar(stand, pl);
            }
        }
    }

    public void unload(Entity entity) {
        if (entity instanceof ArmorStand stand) {
            Vehicle vehicle = VehicleController.VehicleLink.get(stand);
            if (vehicle != null) vehicle.remove();
        }
    }

    public void ride(Entity entity, Player player) {
        if (entity instanceof Interaction interaction) {
            if (interaction.getPassengers().size() != 0 && !VehicleController.SeatList.contains(interaction)) return;
            interaction.addPassenger(player);
        }
    }
}
