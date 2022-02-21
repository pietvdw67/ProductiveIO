package com.infinity.ProductiveIO.operator.resource;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.infinity.ProductiveIO.operator.model.OperatorItem;
import com.infinity.ProductiveIO.operator.repository.OperatorRepository;
import com.infinity.ProductiveIO.operator.service.OperatorService;

@RestController
public class OperatorResource {
	
	@Autowired
	OperatorRepository repository;
	
	@Autowired
	OperatorService operatorService;	

	@GetMapping("/operator/v1")
	public List<OperatorItem> getOperators() {
		return repository.findBySortedByName();
	}	

	@GetMapping("/operator/v1/{id}")
	public OperatorItem getOperatorById(@PathVariable String id) {
		
		return repository.findById(Long.parseLong(id)).orElse(null);
	}	

	@PostMapping("/operator/v1")
	public OperatorItem addOperator(@RequestBody OperatorItem operatorItem) {
		
		// Clear all previous operator entries with this machineid
		if (Objects.nonNull(operatorItem) && Objects.nonNull(operatorItem.getMachineid())) {
			operatorService.removeMachineIdFromAllOperators(operatorItem.getMachineid());
		}
		
		return repository.save(operatorItem);
		
	}	

	@DeleteMapping("/operator/v1/{id}")
	public void delteOperator(@PathVariable String id) {
		
		repository.deleteById(Long.parseLong(id));		
	}	

	@GetMapping("/operatorForDay/v1/{countDate}/{machineId}")
	public OperatorItem getOperatorForDay(@PathVariable String countDate,@PathVariable String machineId) {
		return operatorService.findOperatorForDay(countDate, Long.parseLong(machineId));
		
	}

}
