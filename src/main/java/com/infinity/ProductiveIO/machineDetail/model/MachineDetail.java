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
	private Integer goalamt;
	private Integer uploadmin;
	private Long note;
	
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
	public Integer getGoalamt() {
		return goalamt;
	}
	public void setGoalamt(Integer goalamt) {
		this.goalamt = goalamt;
	}
	public Integer getUploadmin() {
		return uploadmin;
	}
	public void setUploadmin(Integer uploadmin) {
		this.uploadmin = uploadmin;
	}
	public Long getNote() {
		return note;
	}
	public void setNote(Long note) {
		this.note = note;
	}
	
	@Override
	public String toString() {
		return "MachineDetail [id=" + id + ", name=" + name + ", averageval=" + averageval + ", marginval=" + marginval
				+ ", goalamt=" + goalamt + ", uploadmin=" + uploadmin + ", note=" + note + "]";
	}
	
	

	
	


}
