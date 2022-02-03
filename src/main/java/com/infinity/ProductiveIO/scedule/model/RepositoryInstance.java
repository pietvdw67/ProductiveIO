package com.infinity.ProductiveIO.scedule.model;

import org.springframework.beans.factory.annotation.Autowired;

import com.infinity.ProductiveIO.dailyDetail.repository.DailyDetailRepository;
import com.infinity.ProductiveIO.dailyHistory.repository.DailyHistoryRepository;
import com.infinity.ProductiveIO.scedule.repository.ScheduleRepository;

public class RepositoryInstance {
	
	private static RepositoryInstance instance = null;
	private ScheduleRepository scheduleRepository;
	private DailyDetailRepository dailyDetailRepository;	
	private DailyHistoryRepository dailyHistoryRepository;
	
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
	

}
