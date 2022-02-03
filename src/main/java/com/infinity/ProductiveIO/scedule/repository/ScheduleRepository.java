package com.infinity.ProductiveIO.scedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.infinity.ProductiveIO.scedule.model.ScheduleDetail;

public interface ScheduleRepository extends JpaRepository<ScheduleDetail,Long>{
	
	@Query(value = "select * from schedule where rundate < now() and runtime < ?1",nativeQuery = true)
	List<ScheduleDetail> findPastSchedules(java.sql.Time scheduleTime);

}
