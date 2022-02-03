package com.infinity.ProductiveIO.scedule.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="schedule")
public class ScheduleDetail {
	
	private long id;
	private int jobid;
	private java.sql.Date rundate;
	private java.sql.Time runtime;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getJobid() {
		return jobid;
	}
	public void setJobid(int jobid) {
		this.jobid = jobid;
	}
	public java.sql.Date getRundate() {
		return rundate;
	}
	public void setRundate(java.sql.Date rundate) {
		this.rundate = rundate;
	}
	public java.sql.Time getRuntime() {
		return runtime;
	}
	public void setRuntime(java.sql.Time runtime) {
		this.runtime = runtime;
	}
	
	@Override
	public String toString() {
		return "ScheduleDetail [id=" + id + ", jobid=" + jobid + ", rundate=" + rundate + ", runtime=" + runtime + "]";
	}
	
	
	

}
