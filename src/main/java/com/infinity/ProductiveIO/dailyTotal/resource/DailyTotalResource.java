package com.infinity.ProductiveIO.dailyTotal.resource;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.infinity.ProductiveIO.dailyDetail.repository.DailyDetailRepository;
import com.infinity.ProductiveIO.dailyTotal.service.DailyTotalReportService;
import com.infinity.ProductiveIO.model.ItemDetail;

@RestController
public class DailyTotalResource {
	
	Logger logger = Logger.getLogger(DailyTotalResource.class.toString());
	
	@Autowired
	DailyDetailRepository repository;	

	@GetMapping("/dailytotal/TotalsPerDay/v1")
	public List<ItemDetail> getTotalsPerDay() {		
		
		return repository.findTotalPerDay(new java.sql.Date(System.currentTimeMillis()));
	}	

	@GetMapping("/dailytotal/TotalPerSpecificDay/v1/{countdate}")
	public List<ItemDetail> getTotalPerSpecificDay(@PathVariable String countdate) {
		
		LocalDate ld = LocalDate.parse(countdate,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		java.sql.Date countdateFormatted = new java.sql.Date(ld.atStartOfDay().toInstant(ZoneOffset.ofHours(2)).toEpochMilli());
		
		return repository.findTotalPerDay(countdateFormatted);
	}	

	@GetMapping("/dailytotal/buildReport/v1")
	public void buildDailyTotalReport() {
		
		List<ItemDetail> itemDetails = getTotalsPerDay();
		DailyTotalReportService dailyTotalReportService = new DailyTotalReportService();
		dailyTotalReportService.setItemDetails(itemDetails);
		dailyTotalReportService.buildReport();		
	}	

	@GetMapping("/dailytotal/downloadReport/v1")
	public void downloadInterimReport(HttpServletResponse res) throws Exception {
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileName = path.substring(0, path.length() - 1) + "reports\\DailyTotal.xlsx";
		logger.info(fileName);
		
		res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		res.getOutputStream().write(contentOf(fileName));		
	}	

	@GetMapping("/dailytotal/downloadReport/v2")
	public void downloadInterimReportv2(HttpServletResponse res) throws Exception {
		
		List<ItemDetail> itemDetails = getTotalsPerDay();
		DailyTotalReportService dailyTotalReportService = new DailyTotalReportService();
		dailyTotalReportService.setItemDetails(itemDetails);
		dailyTotalReportService.buildReport();		
		
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileName = path.substring(0, path.length() - 1) + "reports\\DailyTotal.xlsx";
		logger.info(fileName);
		
		res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		res.getOutputStream().write(contentOf(fileName));		
	}
	
	private byte[] contentOf(String fileName) throws Exception {		

		return Files.readAllBytes(Paths.get(fileName));
		
	}


}
