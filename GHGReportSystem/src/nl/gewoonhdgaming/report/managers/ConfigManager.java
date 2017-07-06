package nl.gewoonhdgaming.report.managers;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import nl.gewoonhdgaming.report.ReportSystem;

public class ConfigManager {
	
	private final ReportSystem reportSystem = ReportSystem.getPlugin(ReportSystem.class);
	
	private File configFile;
	private FileConfiguration config;
	
	private File dataFile;
	private FileConfiguration data;
	
	public ConfigManager() {
		
		dataFile = new File(reportSystem.getDataFolder(), "data.yml");
		data = YamlConfiguration.loadConfiguration(dataFile);
		
		configFile = new File(reportSystem.getDataFolder(), "config.yml");
		config = YamlConfiguration.loadConfiguration(configFile);
	}
	
	public void save() {
		try {
			config.save(configFile);
			data.save(dataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public File getConfig() {
		return configFile;
	}
	
	public File getData() {
		return dataFile;
	}
	
	public FileConfiguration getConfigFile() {
		return config;
	}
	
	public FileConfiguration getDataFile() {
		return data;
		}

}
