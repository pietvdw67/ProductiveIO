package com.infinity.ProductiveIO.operator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.infinity.ProductiveIO.model.ItemDetail;
import com.infinity.ProductiveIO.operator.model.OperatorItem;

public interface OperatorRepository extends JpaRepository<OperatorItem,Long> {
	
	@Query(value = "SELECT * from operator order by operatorname asc",nativeQuery = true)
	List<OperatorItem> findBySortedByName();
	
	@Query(value = "SELECT * from operator where machineid = ?1",nativeQuery = true)
	List<OperatorItem> findOperatorByMachineId(long machineId);

}
