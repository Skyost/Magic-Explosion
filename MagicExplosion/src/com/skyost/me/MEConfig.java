package com.skyost.me;

import java.io.File;

import org.bukkit.plugin.Plugin;

public class MEConfig extends Config {
	public MEConfig(Plugin plugin) {
		CONFIG_FILE = new File(plugin.getDataFolder(), "config.yml");
		CONFIG_HEADER = "Magic Explosion Configuration";
	}
	public boolean CreateFire = false;
	public boolean LogExplosions = true;
}
