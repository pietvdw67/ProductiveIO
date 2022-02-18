package com.infinity.ProductiveIO.scedule.model;

import org.springframework.jdbc.core.JdbcTemplate;

import com.infinity.ProductiveIO.dailyDetail.dto.DetailJDBC;
import com.infinity.ProductiveIO.dailyDetail.repository.DailyDetailRepository;
import com.infinity.ProductiveIO.dailyHistory.dto.DailyHistoryJDBC;
import com.infinity.ProductiveIO.dailyHistory.repository.DailyHistoryRepository;
import com.infinity.ProductiveIO.machineDetail.repository.MachineDetailRepository;
import com.infinity.ProductiveIO.operator.repository.OperatorRepository;
import com.infinity.ProductiveIO.scedule.repository.ScheduleRepository;
import com.infinity.ProductiveIO.settings.service.SettingsService;

public class RepositoryInstance {
	
	private static RepositoryInstance instance = null;
	private ScheduleRepository scheduleRepository;
	private DailyDetailRepository dailyDetailRepository;	
	private DailyHistoryRepository dailyHistoryRepository;
	private MachineDetailRepository machineDetailRepository;
	private OperatorRepository operatorRepository;
	private JdbcTemplate jdbcTemplate;
	private DailyHistoryJDBC dailyHistoryJdbc;
	private DetailJDBC detailJDBC;
	private SettingsService settingsService;
	
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

	public OperatorRepository getOperatorRepository() {
		return operatorRepository;
	}

	public void setOperatorRepository(OperatorRepository operatorRepository) {
		this.operatorRepository = operatorRepository;
	}

	public DailyHistoryJDBC getDailyHistoryJdbc() {
		return dailyHistoryJdbc;
	}

	public void setDailyHistoryJdbc(DailyHistoryJDBC dailyHistoryJdbc) {
		this.dailyHistoryJdbc = dailyHistoryJdbc;
	}

	public DetailJDBC getDetailJDBC() {
		return detailJDBC;
	}

	public void setDetailJDBC(DetailJDBC detailJDBC) {
		this.detailJDBC = detailJDBC;
	}

	public SettingsService getSettingsService() {
		return settingsService;
	}

	public void setSettingsService(SettingsService settingsService) {
		this.settingsService = settingsService;
	}
	

}
