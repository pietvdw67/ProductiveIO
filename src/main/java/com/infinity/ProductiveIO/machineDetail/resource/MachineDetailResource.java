package com.infinity.ProductiveIO.machineDetail.resource;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
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

	@GetMapping("/machinedetails/v1")
	public List<MachineDetail> getMachineDetails() {
		return repository.findBySortedById();
	}	

	@GetMapping("/machinedetails/v1/{id}")
	public MachineDetail getMachineDetailsById(@PathVariable String id) {
		
		return repository.findById(Long.parseLong(id)).orElse(null);
	}	

	@PostMapping("/machinedetails/v1")
	public MachineDetail addMachineDetail(@RequestBody MachineDetail machineDetail) {
		
		return repository.save(machineDetail);
		
	}	

	@DeleteMapping("/machinedetails/v1/{id}")
	public void delteMachineDetail(@PathVariable String id) {
		
		repository.deleteById(Long.parseLong(id));
		
	}	

	@GetMapping("/machinedetails/uploadmin/v1/{id}")
	public int getMachineUploadMin(@PathVariable String id) {
		
		MachineDetail machineDetail = repository.findById(Long.parseLong(id)).orElse(null);
		
		if (Objects.isNull(machineDetail) || Objects.isNull(machineDetail.getUploadmin())) {
			return 0;
		} else {
			return machineDetail.getUploadmin();
		}
		
	}
	

}
