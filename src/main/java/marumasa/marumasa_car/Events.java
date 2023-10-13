package marumasa.marumasa_car;

import marumasa.marumasa_car.vehicle.VehicleEventManager;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.bukkit.event.world.EntitiesUnloadEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Events implements Listener {

    private final Config cfg;
    private final VehicleEventManager eventManager;

    public Events(Config config, JavaPlugin plugin) {
        cfg = config;
        eventManager = new VehicleEventManager(plugin);
    }

    @EventHandler
    private void onEntitySpawn(EntitySpawnEvent event) {
        eventManager.load(event.getEntity());
    }

    @EventHandler
    private void onEntityDeath(EntityDeathEvent event) {
        eventManager.unload(event.getEntity());
    }

    @EventHandler
    private void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        eventManager.ride(event.getRightClicked(), event.getPlayer());
    }

    @EventHandler
    private void onEntityLoad(EntitiesLoadEvent event) {
        for (Entity entity : event.getEntities()) eventManager.load(entity);
    }

    @EventHandler
    private void onEntitiesUnload(EntitiesUnloadEvent event) {
        for (Entity entity : event.getEntities()) eventManager.unload(entity);
    }
}
