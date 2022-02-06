package com.infinity.ProductiveIO.machineDetail.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="machinedetail")
public class MachineDetail {
	
	private long id;
	private String name;
	private Integer averageval;
	private Integer marginval;
	
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
	public Integer getAverageval() {
		return averageval;
	}
	public void setAverageval(Integer averageval) {
		this.averageval = averageval;
	}
	public Integer getMarginval() {
		return marginval;
	}
	public void setMarginval(Integer marginval) {
		this.marginval = marginval;
	}
	
	@Override
	public String toString() {
		return "MachineDetail [id=" + id + ", name=" + name + ", averageval=" + averageval + ", marginval=" + marginval
				+ "]";
	}
	

}
