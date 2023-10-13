package marumasa.marumasa_car.vehicle;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class VehicleController {
    public static final Set<Interaction> SeatList = new HashSet<>();
    public static Map<ArmorStand, Vehicle> VehicleLink = new HashMap<>();
    public static Set<Player> W = new HashSet<>();
    public static Set<Player> A = new HashSet<>();
    public static Set<Player> S = new HashSet<>();
    public static Set<Player> D = new HashSet<>();
    public static Set<Player> Jump = new HashSet<>();
}
