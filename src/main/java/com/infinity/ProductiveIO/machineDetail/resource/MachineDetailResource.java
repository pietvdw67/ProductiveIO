package com.infinity.ProductiveIO.machineDetail.resource;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.infinity.ProductiveIO.machineDetail.model.MachineDetail;
import com.infinity.ProductiveIO.machineDetail.repository.MachineDetailRepository;

@RestController
public class MachineDetailResource {
	
	Logger logger = Logger.getLogger(MachineDetailResource.class.toString());
	
	@Autowired
	MachineDetailRepository repository;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/machinedetails/v1")
	public List<MachineDetail> getMachineDetails() {
		return repository.findBySortedById();
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/machinedetails/v1/{id}")
	public MachineDetail getMachineDetailsById(@PathVariable String id) {
		
		return repository.findById(Long.parseLong(id)).orElse(null);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/machinedetails/v1")
	public MachineDetail addMachineDetail(@RequestBody MachineDetail machineDetail) {
		
		return repository.save(machineDetail);
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/machinedetails/v1/{id}")
	public void delteMachineDetail(@PathVariable String id) {
		
		repository.deleteById(Long.parseLong(id));
		
	}

}
