package com.skyost.me;

import java.io.File;

import org.bukkit.plugin.Plugin;

public class MEConfigLanguage extends Config {
	public MEConfigLanguage(Plugin plugin) {
		CONFIG_FILE = new File(plugin.getDataFolder(), "lang.yml");
		CONFIG_HEADER = "Magic Explosion Language Configuration";
	}
	public String EnableMessage = "Hello :)";
	public String DisableMessage = "Goodbye !";
	public String PermissionMessage = "You don't have permission to do that !";
	public String ExplosionMessage = "Explosion created by";
}
