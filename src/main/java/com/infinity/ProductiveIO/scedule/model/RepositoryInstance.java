package com.infinity.ProductiveIO.scedule.model;

import org.springframework.jdbc.core.JdbcTemplate;

import com.infinity.ProductiveIO.dailyDetail.repository.DailyDetailRepository;
import com.infinity.ProductiveIO.dailyHistory.repository.DailyHistoryRepository;
import com.infinity.ProductiveIO.machineDetail.repository.MachineDetailRepository;
import com.infinity.ProductiveIO.scedule.repository.ScheduleRepository;

public class RepositoryInstance {
	
	private static RepositoryInstance instance = null;
	private ScheduleRepository scheduleRepository;
	private DailyDetailRepository dailyDetailRepository;	
	private DailyHistoryRepository dailyHistoryRepository;
	private MachineDetailRepository machineDetailRepository;
	private JdbcTemplate jdbcTemplate;
	
	private RepositoryInstance() {}
	
	public static RepositoryInstance getInstance() {
		if (instance == null) {
			instance = new RepositoryInstance();
		}
		
		return instance;
	}

	public ScheduleRepository getScheduleRepository() {
		return scheduleRepository;
	}

	public void setScheduleRepository(ScheduleRepository scheduleRepository) {
		this.scheduleRepository = scheduleRepository;
	}

	public DailyDetailRepository getDailyDetailRepository() {
		return dailyDetailRepository;
	}

	public void setDailyDetailRepository(DailyDetailRepository dailyDetailRepository) {
		this.dailyDetailRepository = dailyDetailRepository;
	}

	public DailyHistoryRepository getDailyHistoryRepository() {
		return dailyHistoryRepository;
	}

	public void setDailyHistoryRepository(DailyHistoryRepository dailyHistoryRepository) {
		this.dailyHistoryRepository = dailyHistoryRepository;
	}

	public MachineDetailRepository getMachineDetailRepository() {
		return machineDetailRepository;
	}

	public void setMachineDetailRepository(MachineDetailRepository machineDetailRepository) {
		this.machineDetailRepository = machineDetailRepository;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	

}
