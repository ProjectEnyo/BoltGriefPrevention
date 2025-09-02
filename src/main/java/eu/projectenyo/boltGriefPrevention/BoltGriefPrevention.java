package eu.projectenyo.boltGriefPrevention;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.ClaimPermission;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.popcraft.bolt.BoltAPI;
import org.popcraft.bolt.event.LockBlockEvent;

public final class BoltGriefPrevention extends JavaPlugin {

	private BoltAPI bolt;

	@Override
	public void onEnable() {
		this.bolt = getServer().getServicesManager().load(BoltAPI.class);
		if (bolt == null) {
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		bolt.registerListener(LockBlockEvent.class, event -> {
			Location lockLocation = event.getBlock().getLocation();
			Player player = event.getPlayer();
			Claim claim = GriefPrevention.instance.dataStore.getClaimAt(lockLocation, true, null);
			if (claim.checkPermission(player, ClaimPermission.Build, null) != null) {
				event.setCancelled(true);
			}
		});
	}

	@Override
	public void onDisable() {
		this.bolt = null;
	}
}
