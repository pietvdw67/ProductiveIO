package com.infinity.ProductiveIO.dailyDetail.resource;

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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.infinity.ProductiveIO.dailyDetail.repository.DailyDetailRepository;
import com.infinity.ProductiveIO.dailyDetail.service.DailyDetailReportService;
import com.infinity.ProductiveIO.dailyTotal.resource.DailyTotalResource;
import com.infinity.ProductiveIO.model.ItemDetail;

@RestController
public class DailyDetailResource {
	
	Logger logger = Logger.getLogger(DailyTotalResource.class.toString());
	
	@Autowired
	DailyDetailRepository repository;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/dailydetail/v1/{countdate}/{machineid}")
	public List<ItemDetail> getDailyDetailsPerMachine(@PathVariable String countdate,@PathVariable String machineid) {
		
		LocalDate ld = LocalDate.parse(countdate,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		java.sql.Date countdateFormatted = new java.sql.Date(ld.atStartOfDay().toInstant(ZoneOffset.ofHours(2)).toEpochMilli());
		
		return repository.findByDateAndMachine(countdateFormatted, Integer.parseInt(machineid));
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/dailydetail/buildReport/v1/{countdate}/{machineid}")
	public void buildDailyDetailReport(@PathVariable String countdate,@PathVariable String machineid) {
		
		List<ItemDetail> itemDetails = getDailyDetailsPerMachine(countdate,machineid);
		DailyDetailReportService dailyDetailReportService = new DailyDetailReportService();
		dailyDetailReportService.setItemDetails(itemDetails);
		dailyDetailReportService.buildReport();		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/dailydetail/downloadReport/v1")
	public void downloadInterimReport(HttpServletResponse res) throws Exception {
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileName = path.substring(0, path.length() - 1) + "reports\\DailyDetail.xlsx";
		logger.info(fileName);
		
		res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		res.getOutputStream().write(contentOf(fileName));
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/dailydetail/downloadReport/v2/{countdate}/{machineid}")
	public void downloadInterimReportc2(HttpServletResponse res,@PathVariable String countdate,@PathVariable String machineid) throws Exception {
		
		List<ItemDetail> itemDetails = getDailyDetailsPerMachine(countdate,machineid);
		DailyDetailReportService dailyDetailReportService = new DailyDetailReportService();
		dailyDetailReportService.setItemDetails(itemDetails);
		dailyDetailReportService.buildReport();
		
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileName = path.substring(0, path.length() - 1) + "reports\\DailyDetail.xlsx";
		logger.info(fileName);
		
		res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		res.getOutputStream().write(contentOf(fileName));
		
	}
	
	private byte[] contentOf(String fileName) throws Exception {		

		return Files.readAllBytes(Paths.get(fileName));		
	}

}
