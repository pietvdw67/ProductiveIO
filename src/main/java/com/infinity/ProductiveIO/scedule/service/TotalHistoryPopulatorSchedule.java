package com.infinity.ProductiveIO.scedule.service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.logging.Logger;

import com.infinity.ProductiveIO.dailyHistory.model.HistoryItem;
import com.infinity.ProductiveIO.model.ItemDetail;
import com.infinity.ProductiveIO.scedule.model.RepositoryInstance;

public class TotalHistoryPopulatorSchedule implements Runnable {
	
	Logger logger = Logger.getLogger(TotalHistoryPopulatorSchedule.class.toString());

	@Override
	public void run() {
		logger.info(" *** Running TotalHistoryPopulatorSchedule ***");

		LocalDate yesterday = LocalDate.now().minusDays(1);;
		java.sql.Date yesterdayFormatted = new java.sql.Date(yesterday.atStartOfDay().toInstant(ZoneOffset.ofHours(2)).toEpochMilli());
		List<ItemDetail> itemDetails = RepositoryInstance.getInstance().getDailyDetailRepository().findTotalPerDay(yesterdayFormatted);
		
		// Delete previous items
		List<HistoryItem> yesterdayItems = RepositoryInstance.getInstance().getDailyHistoryRepository().findByDate(yesterdayFormatted);
		yesterdayItems.forEach(historyItem -> RepositoryInstance.getInstance().getDailyHistoryRepository().delete(historyItem));
		
		// Insert new items
		itemDetails.forEach(itemDetail -> {
			HistoryItem historyItem = new HistoryItem();
			historyItem.setMachineid(itemDetail.getMachineid());
			historyItem.setCountdate(itemDetail.getCountdate());
			historyItem.setCountamount(itemDetail.getCountamount());
			
			RepositoryInstance.getInstance().getDailyHistoryRepository().save(historyItem);			
		});
		
	}

}
