package marumasa.marumasa_car;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import marumasa.marumasa_car.vehicle.TestCar;
import marumasa.marumasa_car.vehicle.Vehicle;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.bukkit.event.world.EntitiesUnloadEvent;

import java.util.Set;

import static marumasa.marumasa_car.vehicle.Vehicle.*;

public class Events implements Listener {

    private final Config cfg;
    private final Minecraft mc;

    public Events(Config config, Minecraft minecraft) {
        cfg = config;
        mc = minecraft;

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(mc, ListenerPriority.HIGHEST, PacketType.Play.Client.STEER_VEHICLE) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                Player player = event.getPlayer();

                Entity b = player.getVehicle();
                if (b instanceof Player) {
                    return;
                }

                if (packet.getFloat().read(1) > 0) {
                    W.add(player);
                } else if (packet.getFloat().read(1) < 0) {
                    S.add(player);
                } else {
                    W.remove(player);
                    S.remove(player);
                }

                if (packet.getFloat().read(0) > 0) {
                    A.add(player);
                } else if (packet.getFloat().read(0) < 0) {
                    D.add(player);
                } else {
                    A.remove(player);
                    D.remove(player);
                }

                if (packet.getBooleans().read(0)) {
                    Jump.add(player);
                } else {
                    Jump.remove(player);
                }
            }
        });
    }

    @EventHandler
    private void onEntitySpawn(EntitySpawnEvent event) {
        Load(event.getEntity());
    }

    @EventHandler
    private void onEntityDeath(EntityDeathEvent event) {
        Unload(event.getEntity());
    }

    @EventHandler
    private void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Ride(event.getRightClicked(), event.getPlayer());
    }

    @EventHandler
    private void onEntityLoad(EntitiesLoadEvent event) {
        for (Entity entity : event.getEntities())
            Load(entity);
    }

    @EventHandler
    private void onEntitiesUnload(EntitiesUnloadEvent event) {
        for (Entity entity : event.getEntities())
            Unload(entity);
    }

    private void Ride(Entity entity, Player player) {
        if (entity instanceof Interaction interaction) {
            if (interaction.getPassengers().size() != 0 && !SeatList.contains(interaction)) return;
            interaction.addPassenger(player);
        }
    }

    private void Load(Entity entity) {
        final Set<String> tags = entity.getScoreboardTags();
        if (entity instanceof ArmorStand stand) {
            if (tags.contains("marumasa.test")) {
                TestCar.createVehicle(stand, mc);
            }
        }
    }

    private void Unload(Entity entity) {
        if (entity instanceof ArmorStand stand) {
            Vehicle vehicle = VehicleController.VehicleLink.get(stand);
            if (vehicle != null) vehicle.remove();
        }
    }
}
