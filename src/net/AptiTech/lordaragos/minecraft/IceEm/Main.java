package net.AptiTech.lordaragos.minecraft.IceEm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
/**
 * TODO Put here a description of what this class does.
 *
 * @author kevint.
 *         Created Jan 13, 2013.
 */
@SuppressWarnings("javadoc")
public class Main extends JavaPlugin{
	public static Main plugin;
	public final Logger logger = Logger.getLogger("Minecraft");
	public final PluginEventHandler eventhandler = new PluginEventHandler(this);
	public String name;
	private Player player;
	public List<Player> frozenplayers = new ArrayList<Player>();
	public List<String> offlinefrozenplayers = new CopyOnWriteArrayList<String>();
	public BukkitTask arraylistiterator;
	
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info("[" + pdfFile.getName() +"']" +" Plugin is now disabled.");
	}
	
	@Override
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this.eventhandler, this);
		this.name = ChatColor.AQUA + "["+pdfFile.getName()+"']" + ChatColor.WHITE;
		this.logger.info(this.name +" Plugin is now enabled.");
		this.arraylistiterator = this.getServer().getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
		    @Override  
		    public void run() {
		        	for(String name:Main.this.offlinefrozenplayers){
		        		if(Main.this.getServer().getPlayer(name) != null) {
			        		Player p = Main.this.getServer().getPlayer(name);
			        		Main.this.frozenplayers.add(p);
			        		Main.this.offlinefrozenplayers.remove(name);
		        		// so if he ever comes back...hes not out of the dog house... :)
		        		
		        			
		        		
		        		
		        		} else {
		        			//pass
		        		}
		        	}
		   }
		}, 60L, 1L);
	}
	
	@SuppressWarnings("unused")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, final String[] args) {
		boolean isplayer;
		if (sender instanceof Player) {
			this.player = (Player) sender;
			isplayer = true;
			World world = this.player.getWorld();
		} else {
			this.player = null;
			
			isplayer = false;
		}
		
		
		String command = commandLabel;
		if (isplayer == true) {
			if(command.equalsIgnoreCase("freeze")) {
				if(this.player.hasPermission("IceEm.freeze")) {
					if(args.length== 0) {
						this.player.sendMessage("/freeze <player>");
						this.player.sendMessage(ChatColor.RED + "Error: Too few arguments, you need a target player.");
					} else if(args.length == 1){
						if (this.player.getServer().getPlayer(args[0]) != null){
							Player targetplayer = this.player.getServer().getPlayer(args[0]);
							if(this.frozenplayers.contains(targetplayer)){
								this.frozenplayers.remove(targetplayer);
								targetplayer.getServer().broadcastMessage(this.name + " " + targetplayer.getDisplayName() + " was defrosted.");
							} else {
								this.frozenplayers.add(targetplayer);
								targetplayer.getServer().broadcastMessage(this.name + " " + targetplayer.getDisplayName() + " was frozen.");
							}
						} else {
							if(!this.offlinefrozenplayers.contains(args[0])){
								this.player.sendMessage(this.name +ChatColor.YELLOW +"NOTICE: Player isn't online, so we'll keep track and IceEm' when they return.");
								this.offlinefrozenplayers.add(args[0]);
								this.player.getServer().broadcastMessage(this.name + " Offline Player " + args[0] + " was frozen");
							} else {
								this.offlinefrozenplayers.remove(args[0]);
								this.player.getServer().broadcastMessage(this.name + " Offline Player " + args[0] + " was defrosted.");
							}
						}
						
					}
				} else {
					this.player.sendMessage(ChatColor.RED + "Error: You do not have permission do to this.");
				}
			}
		} else {
			ConsoleCommandSender console = (ConsoleCommandSender) sender;
			if(command.equalsIgnoreCase("freeze")) {
				if(args.length == 0) {
					console.sendMessage("/freeze <player>");
					console.sendMessage(ChatColor.RED + "Error: Too few arguments, you need a target player.");
				} else if(args.length == 1){
					if (this.getServer().getPlayer(args[0]) != null){
						Player targetplayer = this.getServer().getPlayer(args[0]);
						if(this.frozenplayers.contains(targetplayer)){
							this.frozenplayers.remove(targetplayer);
							targetplayer.getServer().broadcastMessage(this.name + " " + targetplayer.getDisplayName() + " was defrosted.");
						} else {
							this.frozenplayers.add(targetplayer);
							targetplayer.getServer().broadcastMessage(this.name + " " + targetplayer.getDisplayName() + " was frozen.");
						}
					} else {
						if(!this.offlinefrozenplayers.contains(args[0])){
							this.player.sendMessage(this.name +ChatColor.YELLOW +"NOTICE: Player isn't online, so we'll keep track and IceEm' when they return.");
							this.offlinefrozenplayers.add(args[0]);
							this.player.getServer().broadcastMessage(this.name + " Offline Player " + args[0] + " was frozen");
						} else {
							this.offlinefrozenplayers.remove(args[0]);
							this.player.getServer().broadcastMessage(this.name + " Offline Player " + args[0] + " was defrosted.");
						}
					}
					
				}
			}
		}
		return false;
		
	}
}