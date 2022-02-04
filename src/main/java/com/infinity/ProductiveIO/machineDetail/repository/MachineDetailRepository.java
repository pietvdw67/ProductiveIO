package com.infinity.ProductiveIO.machineDetail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.infinity.ProductiveIO.machineDetail.model.MachineDetail;

public interface MachineDetailRepository extends JpaRepository<MachineDetail,Long> {
	
	@Query(value = "SELECT * from machinedetail order by id asc",nativeQuery = true)
	List<MachineDetail> findBySortedById();

}
