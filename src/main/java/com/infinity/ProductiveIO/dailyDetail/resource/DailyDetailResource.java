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
import com.infinity.ProductiveIO.dailyDetail.service.DetailService;
import com.infinity.ProductiveIO.dailyTotal.resource.DailyTotalResource;
import com.infinity.ProductiveIO.model.ItemDetail;
import com.infinity.ProductiveIO.model.ItemDetailView;

@RestController
public class DailyDetailResource {
	
	Logger logger = Logger.getLogger(DailyTotalResource.class.toString());
	
	@Autowired
	DailyDetailRepository repository;
	
	@Autowired
	DetailService detailService;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/dailydetail/v1/{countdate}/{machineid}")
	public List<ItemDetailView> getDailyDetailsPerMachine(@PathVariable String countdate,@PathVariable String machineid) {
		
		java.sql.Date countdateFormatted;
		
		if ( countdate.equalsIgnoreCase("today")) {
			countdateFormatted =  new java.sql.Date(LocalDate.now().atStartOfDay().toInstant(ZoneOffset.ofHours(2)).toEpochMilli());
		} else {
			LocalDate ld = LocalDate.parse(countdate,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			countdateFormatted = new java.sql.Date(ld.atStartOfDay().toInstant(ZoneOffset.ofHours(2)).toEpochMilli());
		}
		
		List<ItemDetail> detailItems = repository.findByDateAndMachine(countdateFormatted, Integer.parseInt(machineid));
		return detailService.ItemDetailToItemDetailView(detailItems);
		
		
	}	
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/dailydetail/downloadReport/v1/{countdate}/{machineid}")
	public void downloadInterimReportc2(HttpServletResponse res,@PathVariable String countdate,@PathVariable String machineid) throws Exception {
		
		List<ItemDetailView> itemDetailViews = getDailyDetailsPerMachine(countdate,machineid);
		DailyDetailReportService dailyDetailReportService = new DailyDetailReportService();
		dailyDetailReportService.setItemDetails(itemDetailViews);
		dailyDetailReportService.buildReport();
		
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileName = path.substring(0, path.length() - 1) + "reports\\Detail.xlsx";
		logger.info(fileName);
		
		res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		res.getOutputStream().write(contentOf(fileName));
		
	}
	
	private byte[] contentOf(String fileName) throws Exception {		

		return Files.readAllBytes(Paths.get(fileName));		
	}

}
