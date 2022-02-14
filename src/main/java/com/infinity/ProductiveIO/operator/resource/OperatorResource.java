package com.infinity.ProductiveIO.operator.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/operator/v1")
	public List<OperatorItem> getOperators() {
		return repository.findBySortedByName();
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/operator/v1/{id}")
	public OperatorItem getOperatorById(@PathVariable String id) {
		
		return repository.findById(Long.parseLong(id)).orElse(null);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/operator/v1")
	public OperatorItem addOperator(@RequestBody OperatorItem operatorItem) {
		
		// Clear all previous operator entries with this machineid
		operatorService.removeMachineIdFromAllOperators(operatorItem.getMachineid());
		
		return repository.save(operatorItem);
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/operator/v1/{id}")
	public void delteOperator(@PathVariable String id) {
		
		repository.deleteById(Long.parseLong(id));		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/operatorForDay/v1/{countDate}/{machineId}")
	public OperatorItem getOperatorForDay(@PathVariable String countDate,@PathVariable String machineId) {
		return operatorService.findOperatorForDay(countDate, Long.parseLong(machineId));
		
	}

}
