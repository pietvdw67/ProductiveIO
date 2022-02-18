package com.infinity.ProductiveIO.settings.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infinity.ProductiveIO.settings.model.Settings;
import com.infinity.ProductiveIO.settings.model.SettingsConfig;
import com.infinity.ProductiveIO.settings.repository.SettingsRepository;

@Service
public class SettingsService {
	
	private static final String HOURS_MONDAY = "hoursMonday";
	private static final String HOURS_TUESDAY = "hoursTuesday";
	private static final String HOURS_WEDNESDAY = "hoursWednesday";
	private static final String HOURS_THURSDAY = "hoursThursday";
	private static final String HOURS_FRIDAY = "hoursFriday";
	private static final String HOURS_SATURDAY = "hoursSaturday";
	private static final String HOURS_SUNDAY = "hoursSunday";
		
	@Autowired
	SettingsRepository repository;
	
	private static SettingsService instance;
	
	private SettingsService() {}	
		
	Logger logger = Logger.getLogger(SettingsService.class.toString());
	
	public static SettingsService getInstance() {
		if (instance == null) {
			instance = new SettingsService();
			
			instance.loadSettings();
		}
		
		return instance;
	}
	
	private void loadSettings() {		
		
		List<Settings> settingsRetrieved = repository.findAll();
		settingsRetrieved.forEach(setting -> {
			AppSettings.getInstance().getSettingsMap().put(setting.getSettingkey(), setting);			
		});	
		
	}
	
	public String getSetting(String key) {
		if (AppSettings.getInstance().getSettingsMap().containsKey(key)) {
			return AppSettings.getInstance().getSettingsMap().get(key).getSettingvalue();
		} else {
			return "";
		}
	}
	
	public double getSettingDouble(String key) {
		
		String retrieved = getSetting(key);
		if (retrieved.length() == 0) {
			return 0;
		} else {
			return Double.parseDouble(retrieved);
		}
		
	}
	
	public void setSetting(String key,String value) {
		if (AppSettings.getInstance().getSettingsMap().containsKey(key)) {
			Settings settings = AppSettings.getInstance().getSettingsMap().get(key);
			settings.setSettingvalue(value);
			
			repository.save(settings);			
		} else {
			Settings newSettings = new Settings();
			newSettings.setSettingkey(key);
			newSettings.setSettingvalue(value);
						
			repository.save(newSettings);
			AppSettings.getInstance().getSettingsMap().put(newSettings.getSettingkey() ,newSettings);
		}
	}
	
	public SettingsConfig getSettingsConfig() {
		SettingsConfig config = new SettingsConfig();
		config.setHoursMonday(getSettingDouble(HOURS_MONDAY));
		config.setHoursTuesday(getSettingDouble(HOURS_TUESDAY));
		config.setHoursWednesday(getSettingDouble(HOURS_WEDNESDAY));
		config.setHoursThursday(getSettingDouble(HOURS_THURSDAY));
		config.setHoursFriday(getSettingDouble(HOURS_FRIDAY));
		config.setHoursSaturday(getSettingDouble(HOURS_SATURDAY));
		config.setHoursSunday(getSettingDouble(HOURS_SUNDAY));
				
		return config;		
	}
	
	public void setSettingsConfig(SettingsConfig config) {
		setSetting(HOURS_MONDAY, String.valueOf(config.getHoursMonday()));
		setSetting(HOURS_TUESDAY, String.valueOf(config.getHoursTuesday()));
		setSetting(HOURS_WEDNESDAY, String.valueOf(config.getHoursWednesday()));
		setSetting(HOURS_THURSDAY, String.valueOf(config.getHoursThursday()));
		setSetting(HOURS_FRIDAY, String.valueOf(config.getHoursFriday()));
		setSetting(HOURS_SATURDAY, String.valueOf(config.getHoursSaturday()));
		setSetting(HOURS_SUNDAY, String.valueOf(config.getHoursSunday()));
	}

}
