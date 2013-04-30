package com.skyost.me;

import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	public MEConfig config;
	public MEConfigLanguage configLanguage;
	public static boolean createFire;
	public static boolean logExplosions;
	public static String enableMessage = null;
	public static String disableMessage = null;
	public static String permissionMessage = null;
	public static String explosionMessage = null;
	
	@Override
    public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
			try {
				System.setOut(new PrintStream(System.out, true, "cp850"));
				config = new MEConfig(this);
            	config.init();
            	configLanguage = new MEConfigLanguage(this);
            	configLanguage.init();
            	createFire = config.CreateFire;
            	logExplosions = config.LogExplosions;
            	setLanguage();
				}
			catch(Exception ex) {
				getLogger().log(Level.SEVERE, "[Magic Explosion] " + ex);
				getServer().getPluginManager().disablePlugin(this);
            return;
			}
		startMetricsLite();
		System.out.println("[Magic Explosion] " + enableMessage);
    }
 
    @Override
    public void onDisable() {
    	System.out.println("[Magic Explosion] " + disableMessage);
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
    	{
            Player Player = e.getPlayer();
            if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return; {
            	ItemStack IE = e.getItem();
            	ItemStack IEe = new ItemStack(Material.FEATHER);
            		if(Player.hasPermission("magicexplosion.player.usefeather")) {
            			try {
            				if(IE.equals(IEe)) {
            					Player.getWorld().createExplosion(Player.getTargetBlock(null, 0).getLocation(), 4F, createFire);
            						if(logExplosions = true){
            							System.out.println("[Magic Explosion] " + explosionMessage + " " + Player.getName() + " !");
            						}
            				}
            			}
            		catch(NullPointerException e2) {
            		
            			}
            		}
            	else {
					Player.sendMessage("[Magic Explosion] " + permissionMessage);
            	}
            }
    	}
    }
    
	public void setLanguage() {
    	enableMessage = configLanguage.EnableMessage;
    	disableMessage = configLanguage.DisableMessage;
    	permissionMessage = configLanguage.PermissionMessage;
    	explosionMessage = configLanguage.ExplosionMessage;
    }
    
	public void startMetricsLite() {
		try {
		    MetricsLite metrics = new MetricsLite(this);
		    metrics.start();
		} catch (IOException e3) {
			System.out.println("[Magic Explosion] " + e3);
		}
	}
}