package com.infinity.ProductiveIO.itemCounter.resource;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.infinity.ProductiveIO.dailyDetail.repository.DailyDetailRepository;
import com.infinity.ProductiveIO.itemCounter.model.ItemCount;
import com.infinity.ProductiveIO.model.ItemDetail;

@RestController
public class ItemCounterResource {
	
	@Autowired
	DailyDetailRepository repository;
	
	Logger logger = Logger.getLogger(ItemCounterResource.class.toString());
	
	@PostMapping("/itemCounter/v1")
	public void itemCounter(@RequestBody ItemCount item) {
		
		logger.info("Machine Id: " + item.getMachineId());
		logger.info("Amount: " + item.getAmount());
		
		ItemDetail itemDetail = new ItemDetail();
		itemDetail.setMachineid(item.getMachineId());
		itemDetail.setCountamount(item.getAmount());
		itemDetail.setCountdate(new java.sql.Date(System.currentTimeMillis()));
		itemDetail.setCounttime(new java.sql.Time(System.currentTimeMillis()));
		
		logger.info("Adding to repository");
		repository.save(itemDetail);
		logger.info("Done adding to repository");
		
	}

}
