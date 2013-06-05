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

import com.skyost.me.Metrics.Graph;

public class MEPlugin extends JavaPlugin implements Listener
{
	public int totalExplosions;
	public MEConfig config;
	public MEConfigLanguage configLanguage;
	public static boolean createFire;
	public static boolean logExplosions;
	public static boolean autoUpdate;
	public static String enableMessage = null;
	public static String disableMessage = null;
	public static String permissionMessage = null;
	public static String explosionMessage = null;
	public static String updateSuccess = null;
	public static String noUpdate = null;
	public static String failDownload = null;
	public static String failDBO = null;
	public static String failNoVersion = null;
	public static String failBadSlug = null;
	public static String UpdateAvailable = null;
	
	@Override
    public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		loadConfig();
		update();
		startMetrics();
		System.out.println("[Magic Explosion] " + enableMessage);
    }
	
	public void loadConfig() {
		try {
			System.setOut(new PrintStream(System.out, true, "cp850"));
			config = new MEConfig(this);
			config.init();
			configLanguage = new MEConfigLanguage(this);
			configLanguage.init();
			createFire = config.CreateFire;
			logExplosions = config.LogExplosions;
			autoUpdate = config.AutoUpdateOnLoad;
			setLanguage();
			}
		catch(Exception ex) {
			getLogger().log(Level.SEVERE, "[Magic Explosion] " + ex);
			getServer().getPluginManager().disablePlugin(this);
        return;
		}
	}
	
	public void update() {
		if(autoUpdate == true) {
			try {
				Updater updater = new Updater(this, "magic-explosion", this.getFile(), Updater.UpdateType.DEFAULT, false);
				Updater.UpdateResult result = updater.getResult();
	        		switch(result) {
	            		case SUCCESS:
	            			System.out.println("[Magic Explosion] " + enableMessage);
	            			break;
	            		case NO_UPDATE:
	            			System.out.println("[Magic Explosion] " + noUpdate);
	            			break;
	            		case FAIL_DOWNLOAD:
	            			System.out.println("[Magic Explosion] " + failDownload);
	            			break;
	            		case FAIL_DBO:
	            			System.out.println("[Magic Explosion] " + failDBO);
	            			break;
	            		case FAIL_NOVERSION:
	            			System.out.println("[Magic Explosion] " + failNoVersion);
	            			break;
	            		case FAIL_BADSLUG:
	            			System.out.println("[Magic Explosion] " + failBadSlug);
	            			break;
	            		case UPDATE_AVAILABLE:
	            			System.out.println("[Magic Explosion] " + UpdateAvailable);
	            			break;
	        		}
				}	
			catch (Exception ex) {
				getLogger().log(Level.SEVERE, "[Magic Explosion] " + ex);
			}
		}
		else {
			doNothing();
		}
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
            					totalExplosions = totalExplosions + 1;
            						if(logExplosions == true) {
            							System.out.println("[Magic Explosion] " + explosionMessage + " " + Player.getName() + " !");
            						}
            						else {
            							doNothing();
            						}
            				}
            			}
            		catch(NullPointerException e2) {
            				doNothing();
            			}
            		}
            	else {
					Player.sendMessage("[Magic Explosion] " + permissionMessage);
            	}
            }
    	}
    }
    
    public void doNothing() {
    	
    }
    
	public void setLanguage() {
    	enableMessage = configLanguage.EnableMessage;
    	disableMessage = configLanguage.DisableMessage;
    	permissionMessage = configLanguage.PermissionMessage;
    	explosionMessage = configLanguage.ExplosionMessage;
    	updateSuccess = configLanguage.Update_SUCCESS;
    	noUpdate = configLanguage.Update_NO_UPDATE;
    	failDownload = configLanguage.Update_FAIL_DOWNLOAD;
    	failDBO = configLanguage.Update_FAIL_DBO;
    	failNoVersion = configLanguage.Update_FAIL_NOVERSION;
    	failBadSlug = configLanguage.Update_FAIL_BADSLUG;
    	UpdateAvailable = configLanguage.Update_UPDATE_AVAILABLE;
    }
    
	public void startMetrics() {
		try {
		    Metrics metrics = new Metrics(this);
		    Graph explosionGraph = metrics.createGraph("Explosions");
		    explosionGraph.addPlotter(new Metrics.Plotter("Total explosions") {
		    @Override
		    public int getValue() {
		        return totalExplosions;	
		       }
		    });
		    
    		Graph updateGraph = metrics.createGraph("Update Graph");
    		updateGraph.addPlotter(new Metrics.Plotter("Checking for Updates") {	
    			@Override
    			public int getValue() {	
    				return 1;
    			}
    			
    			@Override
    			public String getColumnName() {
    				if(autoUpdate == true) {
    					return "Yes";
    				}
    				else if (autoUpdate == false) {
    					return "No";
    				}
    				else {
    					return "Maybe";
    				}
    			}
    		});
		    metrics.start();
		} catch (IOException e3) {
			System.out.println("[Magic Explosion] " + e3);
		}
	}
}