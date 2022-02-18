package com.infinity.ProductiveIO.settings.resource;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.infinity.ProductiveIO.settings.model.SettingsConfig;
import com.infinity.ProductiveIO.settings.service.SettingsService;

@RestController
public class SettingsResource {
	
	@Autowired
	SettingsService service;
	
	Logger logger = Logger.getLogger(SettingsResource.class.toString());
	
	@GetMapping("/settings/settingsConfig/v1")
	public SettingsConfig getSettingsConfig() {
		return service.getSettingsConfig();
	}
	
	@PostMapping("/settings/settingsConfig/v1")
	public void updateSettingsConfig(@RequestBody SettingsConfig settingsConfig) {		
		service.setSettingsConfig(settingsConfig);
	}

}
