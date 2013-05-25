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
	
	public String Update_SUCCESS = "Update found: The updater found an update, and has readied it to be loaded the next time the server restarts/reloads.";
	public String Update_NO_UPDATE = "No Update: The updater did not find an update, and nothing was downloaded.";
	public String Update_FAIL_DOWNLOAD = "Download Failed: The updater found an update, but was unable to download it.";
	public String Update_FAIL_DBO = "dev.bukkit.org Failed: For some reason, the updater was unable to contact DBO to download the file.";
	public String Update_FAIL_NOVERSION = "No version found: When running the version check, the file on DBO did not contain the a version in the format 'vVersion' such as 'v1.0'.";
	public String Update_FAIL_BADSLUG = "Bad slug: The slug provided by the plugin running the updater was invalid and doesn't exist on DBO.";
	public String Update_UPDATE_AVAILABLE = "Update found: There was an update found but not be downloaded !";
}
