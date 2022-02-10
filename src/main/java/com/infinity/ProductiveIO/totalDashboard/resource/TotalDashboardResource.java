package com.infinity.ProductiveIO.totalDashboard.resource;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infinity.ProductiveIO.totalDashboard.model.TotalDashboardItem;
import com.infinity.ProductiveIO.totalDashboard.service.TotalDashboardService;

@RestController
public class TotalDashboardResource {
	
	@Autowired
	TotalDashboardService service;
	
	Logger logger = Logger.getLogger(TotalDashboardResource.class.toString());
		
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/totalDashboard/v1/")
	public List<TotalDashboardItem> getTotalDashboard(){
		return service.getTotalDashboard();
	}

}
