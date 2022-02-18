package com.infinity.ProductiveIO.dataGenerator.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;
import java.util.logging.Logger;

import com.infinity.ProductiveIO.dailyDetail.repository.DailyDetailRepository;
import com.infinity.ProductiveIO.model.ItemDetail;

public class MachineEmulateRunnable  implements Runnable {
	
	DailyDetailRepository repository;
	
	Logger logger = Logger.getLogger(MachineEmulateRunnable.class.toString());	
	
	private static final int INCREMENT_MINUTE = 5;
	private static final int MACHINE_ID_COUNT = 5;
	
	@Override
	public void run() {
		
		logger.info("*** Running MachineEmulateRunnable ***");
		
		do {
			Random random = new Random();
			LocalDateTime time = LocalDateTime.now();
			
			for ( int rep = 0; rep < MACHINE_ID_COUNT; rep++) {				
				
				ItemDetail itemDetail = new ItemDetail();
				itemDetail.setMachineid(rep+1);
				
				int countAmountRandom = random.nextInt(20);
				if (countAmountRandom <= 5) {
					countAmountRandom = 0;
				}
				
				itemDetail.setCountamount(countAmountRandom);
				itemDetail.setCountdate(new java.sql.Date(time.toInstant(ZoneOffset.ofHours(2)).toEpochMilli()));
				itemDetail.setCounttime(new java.sql.Time(time.toInstant(ZoneOffset.ofHours(2)).toEpochMilli()));
				
				repository.save(itemDetail);
			}
			
			try {
				Thread.sleep(INCREMENT_MINUTE * 60000);
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
			
		} while (true);
		
	}

	public DailyDetailRepository getRepository() {
		return repository;
	}

	public void setRepository(DailyDetailRepository repository) {
		this.repository = repository;
	}	
	
}
