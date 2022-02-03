package com.infinity.ProductiveIO.dailyHistory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.infinity.ProductiveIO.dailyHistory.model.HistoryItem;

public interface DailyHistoryRepository extends JpaRepository<HistoryItem,Long> {
	
	@Query(value = "SELECT * from dailyhistory where countdate = ?1 order by machineid",nativeQuery = true)
	List<HistoryItem> findByDate(java.sql.Date searchDate);
	
	@Query(value = "SELECT * from dailyhistory where machineid = ?1 order by countdate desc",nativeQuery = true)
	List<HistoryItem> findByMachineId(int machineId);
}
