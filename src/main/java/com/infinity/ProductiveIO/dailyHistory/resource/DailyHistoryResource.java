package com.infinity.ProductiveIO.dailyHistory.resource;

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

import com.infinity.ProductiveIO.dailyHistory.model.HistoryItem;
import com.infinity.ProductiveIO.dailyHistory.repository.DailyHistoryRepository;
import com.infinity.ProductiveIO.dailyHistory.service.DailyHistoryReportService;

@RestController
public class DailyHistoryResource {
	
	Logger logger = Logger.getLogger(DailyHistoryResource.class.toString());
	
	@Autowired
	DailyHistoryRepository repository;	
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/dailyhistory/v1/{machineid}")
	public List<HistoryItem> getDailyHistoryPerMachine(@PathVariable String machineid) {
		
		return repository.findByMachineId(Integer.parseInt(machineid));
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/dailyhistoryByDate/v1/{countdate}")
	public List<HistoryItem> getDailyHistoryByDate(@PathVariable String countdate) {
		
		LocalDate ld;
		
		if (countdate.toLowerCase().equalsIgnoreCase("yesterday")) {
			ld = LocalDate.now().minusDays(1);
		} else {
			ld = LocalDate.parse(countdate,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		
		java.sql.Date countdateFormatted = new java.sql.Date(ld.atStartOfDay().toInstant(ZoneOffset.ofHours(2)).toEpochMilli());
		
		return repository.findByDate(countdateFormatted);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")	
	@GetMapping("/dailyhistory/buildReport/v1/{machineid}")
	public void buildDailyHistoryReport(@PathVariable String machineid) {
		
		List<HistoryItem> historyItems = repository.findByMachineId(Integer.parseInt(machineid));
		DailyHistoryReportService dailyHistoryReportService = new DailyHistoryReportService();
		dailyHistoryReportService.setHistoryItems(historyItems);
		dailyHistoryReportService.buildReport();		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/dailyhistory/downloadReport/v1")
	public void downloadInterimReport(HttpServletResponse res) throws Exception {
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileName = path.substring(0, path.length() - 1) + "reports\\DailyHistory.xlsx";
		logger.info(fileName);
		
		res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		res.getOutputStream().write(contentOf(fileName));
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/dailyhistory/downloadReport/v2/{machineid}")
	public void downloadInterimReportV2(HttpServletResponse res,@PathVariable String machineid) throws Exception {
		
		List<HistoryItem> historyItems = repository.findByMachineId(Integer.parseInt(machineid));
		DailyHistoryReportService dailyHistoryReportService = new DailyHistoryReportService();
		dailyHistoryReportService.setHistoryItems(historyItems);
		dailyHistoryReportService.buildReport();
		
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileName = path.substring(0, path.length() - 1) + "reports\\DailyHistory.xlsx";
		logger.info(fileName);
		
		res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		res.getOutputStream().write(contentOf(fileName));
		
	}	
	
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/dailyhistory/downloadByDateReport/v1/{countdate}")
	public void downloadByDateReportV1(HttpServletResponse res,@PathVariable String countdate) throws Exception {
		
		List<HistoryItem> historyItems = getDailyHistoryByDate(countdate);		
		DailyHistoryReportService dailyHistoryReportService = new DailyHistoryReportService();
		dailyHistoryReportService.setHistoryItems(historyItems);
		dailyHistoryReportService.buildReport();
		
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileName = path.substring(0, path.length() - 1) + "reports\\DailyHistory.xlsx";
		logger.info(fileName);
		
		res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		res.getOutputStream().write(contentOf(fileName));
		
	}	
		
	private byte[] contentOf(String fileName) throws Exception {		

		return Files.readAllBytes(Paths.get(fileName));		
	}

}