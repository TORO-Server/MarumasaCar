package marumasa.marumasa_car;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.bukkit.event.world.EntitiesUnloadEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

public class Events implements Listener {

    private final Config cfg;
    private final MarumasaCar mc;

    public Events(Config config, MarumasaCar minecraft) {
        cfg = config;
        mc = minecraft;
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
            Interaction display = MarumasaCar.SeatLink.get(interaction);

            if (display == null) return;
            display.addPassenger(player);
        }
    }

    private void Load(Entity entity) {
        new waitLoad(entity).runTaskLater(mc, 5);
    }

    private void Unload(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
        }
    }

    private class waitLoad extends BukkitRunnable {

        private final Entity entity;

        public waitLoad(Entity entity) {
            this.entity = entity;
        }

        @Override
        public void run() {
            final Set<String> tags = entity.getScoreboardTags();
            if (tags.contains("marumasa.test")) {

                if (entity instanceof ArmorStand armorStand) {
                    final TestCar test = new TestCar(armorStand);
                    test.runTaskTimer(mc, 0, 0);
                }
            }
        }
    }
}
