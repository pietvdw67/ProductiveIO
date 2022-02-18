package com.infinity.ProductiveIO.settings.service;

import java.util.HashMap;
import java.util.Map;

import com.infinity.ProductiveIO.settings.model.Settings;

public class AppSettings {

	private AppSettings() {}
	private static AppSettings instance = null;
	private boolean hasLoaded = false;
	
	private Map<String,Settings> settingsMap = new HashMap<>();
	
	public static AppSettings getInstance() {
		if (instance == null) {
			instance = new AppSettings();
			
			instance.setSettingsMap(new HashMap<>());
		}
		
		return instance;		
	}

	public Map<String, Settings> getSettingsMap() {
		return settingsMap;
	}

	protected void setSettingsMap(Map<String, Settings> settingsMap) {
		this.settingsMap = settingsMap;
	}
	public boolean isHasLoaded() {
		return hasLoaded;
	}

	public void setHasLoaded(boolean hasLoaded) {
		this.hasLoaded = hasLoaded;
	}
	
	

}
