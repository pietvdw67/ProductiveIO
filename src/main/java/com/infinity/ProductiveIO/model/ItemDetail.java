package com.infinity.ProductiveIO.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dailydetail")
public class ItemDetail {
	
	private long id;
	private int machineid;
	private java.sql.Date countdate;
	private java.sql.Time counttime;
	private int countamount;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getMachineid() {
		return machineid;
	}
	public void setMachineid(int machineid) {
		this.machineid = machineid;
	}
	public java.sql.Date getCountdate() {
		return countdate;
	}
	public void setCountdate(java.sql.Date countdate) {
		this.countdate = countdate;
	}
	public java.sql.Time getCounttime() {
		return counttime;
	}
	public void setCounttime(java.sql.Time counttime) {
		this.counttime = counttime;
	}
	public int getCountamount() {
		return countamount;
	}
	public void setCountamount(int countamount) {
		this.countamount = countamount;
	}
	
	@Override
	public String toString() {
		return "ItemDetail [id=" + id + ", machineid=" + machineid + ", countdate=" + countdate + ", counttime="
				+ counttime + ", countamount=" + countamount + "]";
	}
		

}
