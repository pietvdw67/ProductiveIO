package com.infinity.ProductiveIO.dataGenerator.runners;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.infinity.ProductiveIO.dailyDetail.repository.DailyDetailRepository;
import com.infinity.ProductiveIO.dailyHistory.repository.DailyHistoryRepository;
import com.infinity.ProductiveIO.model.ItemDetail;

@Component
public class InsertDailyDetailCommandLineRunner implements CommandLineRunner {
	
	Logger logger = Logger.getLogger(InsertDailyDetailCommandLineRunner.class.toString());
		
	@Autowired
	DailyDetailRepository repository;
	
	@Autowired
	DailyHistoryRepository dailyHistoryRepository;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static final boolean DO_RUN = false;
	
	private static final String START_DAY = "09/02/2022";
	private static final String END_DAY = "19/02/2022";
	private static final String START_TIME = "08:00";
	private static final String END_TIME = "08:40";

	private static final int MACHINE_ID_COUNT = 5;
	private static final int INCREMENT_MINUTE = 5;
	private static final int MAX_AMOUNT	 = 20;
	private static final int MARGIN = 5;
	
	// Must have 100 value
	private static final int MARGIN_PERCENT = 70;
	private static final int INACTIVE_PERCENT = 20;
	private static final int OFFLINE_PERCENT = 10;	
	
	@Override
	public void run(String... args) throws Exception {
		if (!DO_RUN) {
			return;
		}
		
		logger.info("*** Running InsertDailyDetailCommandLineRunner ***");
		
		Random random = new Random();
		boolean doLoop = true;
		boolean isNextDay = false;
		String sql = "";
		LocalDateTime currentEndOfDayTime;
		ItemDetail itemDetail;
		
		LocalDateTime currentTime = LocalDateTime.parse(START_DAY + " " + START_TIME,DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		LocalDateTime endTime = LocalDateTime.parse(END_DAY + " " + END_TIME,DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		
		sql = "delete from dailydetail where countdate = '" + currentTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) + "'";
		jdbcTemplate.execute(sql);
		
		do {
			currentTime = currentTime.plusMinutes(INCREMENT_MINUTE);
			currentEndOfDayTime = LocalDateTime.parse(currentTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " " + END_TIME,DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
			
			if (currentTime.isAfter(currentEndOfDayTime)) {
				currentTime = currentTime.plusDays(1);
				currentTime = LocalDateTime.parse(currentTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " " + START_TIME,DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));				
				isNextDay = true;
			}			
			
			if (currentTime.isEqual(endTime) || currentTime.isAfter(endTime)) {
				doLoop = false;	
			}	
			
			if (isNextDay) {
				isNextDay = false;
				sql = "delete from dailydetail where countdate = '" + currentTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) + "'";
				jdbcTemplate.execute(sql);
			}
						
			if (doLoop) {
				for ( int rep = 0; rep < MACHINE_ID_COUNT; rep++) {
					itemDetail = new ItemDetail();
					itemDetail.setMachineid(rep+1);
					
					int randomValue = random.nextInt(100);
					
					// offline
					if (randomValue < OFFLINE_PERCENT) {
						continue;
					}
					
					// inactive
					if (randomValue < INACTIVE_PERCENT) {
						itemDetail.setCountamount(0);
					} else {
						// valid entry
						
						// in margin
						if (randomValue < MARGIN_PERCENT) {
							int randomInsertValue = random.nextInt(MARGIN * 2);
							itemDetail.setCountamount(MAX_AMOUNT-MARGIN + randomInsertValue);
						} else {
							int randomInsertValue = random.nextInt(MAX_AMOUNT-MARGIN);
							itemDetail.setCountamount(randomInsertValue);
						}						
					}
					
					
					itemDetail.setCountdate(new java.sql.Date(currentTime.toInstant(ZoneOffset.ofHours(2)).toEpochMilli()));
					itemDetail.setCounttime(new java.sql.Time(currentTime.toInstant(ZoneOffset.ofHours(2)).toEpochMilli()));					
					
					repository.save(itemDetail);
				}				
				
			}			
			
		} while (doLoop);
		
		logger.info("*** Done with InsertDailyDetailCommandLineRunner ***");		
	}

}
