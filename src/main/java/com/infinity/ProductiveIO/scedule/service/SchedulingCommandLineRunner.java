package com.infinity.ProductiveIO.scedule.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.infinity.ProductiveIO.dailyDetail.repository.DailyDetailRepository;
import com.infinity.ProductiveIO.dailyHistory.repository.DailyHistoryRepository;
import com.infinity.ProductiveIO.machineDetail.repository.MachineDetailRepository;
import com.infinity.ProductiveIO.operator.repository.OperatorRepository;
import com.infinity.ProductiveIO.scedule.model.RepositoryInstance;
import com.infinity.ProductiveIO.scedule.repository.ScheduleRepository;

@Component
public class SchedulingCommandLineRunner implements CommandLineRunner {
		
	Logger logger = Logger.getLogger(SchedulingCommandLineRunner.class.toString());
	
	@Autowired
	ScheduleRepository scheduleRepository;
	
	@Autowired
	DailyDetailRepository dailyDetailRepository;
	
	@Autowired
	DailyHistoryRepository dailyHistoryRepository;
	
	@Autowired
	MachineDetailRepository machineDetailRepository;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	OperatorRepository operatorRepository;

	@Override
	public void run(String... args) throws Exception {
		logger.info(" *** Starting Scheduling service ***");
		
		RepositoryInstance.getInstance().setScheduleRepository(scheduleRepository);
		RepositoryInstance.getInstance().setDailyDetailRepository(dailyDetailRepository);
		RepositoryInstance.getInstance().setDailyHistoryRepository(dailyHistoryRepository);
		RepositoryInstance.getInstance().setMachineDetailRepository(machineDetailRepository);
		RepositoryInstance.getInstance().setJdbcTemplate(jdbcTemplate);
		RepositoryInstance.getInstance().setOperatorRepository(operatorRepository);
		
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(new ScheduleRunner(), 0, 30,TimeUnit.MINUTES);
		
	}

}
