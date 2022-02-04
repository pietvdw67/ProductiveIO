package com.infinity.ProductiveIO.machineDetail.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="machinedetail")
public class MachineDetail {
	
	private long id;
	private String name;
	
	@Id	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "MachineDetail [id=" + id + ", name=" + name + "]";
	}
	
	
	


	
	

}
