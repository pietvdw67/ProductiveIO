package com.infinity.ProductiveIO.scedule.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.infinity.ProductiveIO.scedule.model.RepositoryInstance;
import com.infinity.ProductiveIO.scedule.model.ScheduleDetail;

public class ScheduleRunner implements Runnable {
	
	Logger logger = Logger.getLogger(ScheduleRunner.class.toString());

	@Override
	public void run() {
		
		logger.info("*** Running ScheduleRunner ***");
		
		List<ScheduleDetail> scheduleDetails = RepositoryInstance.getInstance().getScheduleRepository().findPastSchedules(new java.sql.Time(LocalDateTime.now().toInstant(ZoneOffset.ofHours(2)).toEpochMilli()));
		
		scheduleDetails.forEach(scheduleDetail -> startScheduleItem(scheduleDetail));
		
	}
	
	private void startScheduleItem(ScheduleDetail scheduleDetail) {
		
		Runnable runnable = null;
		
		switch (scheduleDetail.getJobid()) {
			case 1: runnable = new  TotalHistoryPopulatorSchedule();
				break;
		}
		
		if (Objects.nonNull(runnable)) {
			Thread t = new Thread(runnable);
			t.start();
		}
		
		LocalDate dt = LocalDate.now().plusDays(1);
		scheduleDetail.setRundate(new java.sql.Date(dt.atStartOfDay().toInstant(ZoneOffset.ofHours(2)).toEpochMilli()));
				
		RepositoryInstance.getInstance().getScheduleRepository().save(scheduleDetail);
		
	}
	
	

}
