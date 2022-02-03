package com.infinity.ProductiveIO.dailyDetail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.infinity.ProductiveIO.model.ItemDetail;

public interface DailyDetailRepository extends JpaRepository<ItemDetail,Long>  {
	
	@Query(value = "SELECT * from dailydetail where countdate = ?1 order by machineid",nativeQuery = true)
	List<ItemDetail> findByDate(java.sql.Date searchDate);
	
	@Query(value = "SELECT * from dailydetail where countdate = ?1 and machineid = ?2 order by machineid",nativeQuery = true)
	List<ItemDetail> findByDateAndMachine(java.sql.Date searchDate,int machineid);
	
	@Query(value = "select distinct cast (machineid as int) from dailydetail where countdate = ?1 order by machineid",nativeQuery = true)
	List<Integer> findUniqueMachineIdForDate(java.sql.Date date);
	
	@Query(value = "select machineid as id,countdate,machineid,sum(countamount) as countamount,max(counttime) as counttime from dailydetail where countdate = ?1 group by machineid,countdate order by machineid",nativeQuery = true)
	List<ItemDetail> findTotalPerDay(java.sql.Date date);

}
