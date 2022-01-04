package net.AptiTech.lordaragos.minecraft.IceEm;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * TODO Handles Player so they won't break anything while frozen
 *
 * @author [AptiTech] lordaragos.
 *         Created Jan 13, 2013.
 */
@SuppressWarnings({ "javadoc" })
public class PluginEventHandler implements Listener {
	public static Main plugin;
	public PluginEventHandler(Main instance){
		plugin = instance;	
	}
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		if(plugin.frozenplayers.contains(event.getPlayer())) {
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent event){
		if(plugin.frozenplayers.contains(event.getPlayer())) {
			event.setCancelled(true);
			event.setBuild(false);
		}
	}
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event){
		if(plugin.frozenplayers.contains(event.getPlayer())) {
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		if(plugin.frozenplayers.contains(event.getPlayer())) {
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event){
		if(plugin.frozenplayers.contains(event.getPlayer())) {
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event){
		if(plugin.frozenplayers.contains(event.getPlayer())) {
			plugin.logger.info(plugin.name + " Frozen Players: " + plugin.frozenplayers.toString());
		}
	}
	@EventHandler
	public void OnPlayerLeave(PlayerQuitEvent event){
		Player p = event.getPlayer();
		if(plugin.frozenplayers.contains(p)) {
			plugin.offlinefrozenplayers.add(p.getName());
		}
	}
	
}
