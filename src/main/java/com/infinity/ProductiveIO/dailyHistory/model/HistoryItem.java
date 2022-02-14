package com.infinity.ProductiveIO.dailyHistory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dailyhistory")
public class HistoryItem {
	
	private long id;
	private int machineid;
	private java.sql.Date countdate;
	private int countamount;
	private Long operatorid;
	private Long noteid;
	
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
	public int getCountamount() {
		return countamount;
	}
	public void setCountamount(int countamount) {
		this.countamount = countamount;
	}
	public Long getOperatorid() {
		return operatorid;
	}
	public void setOperatorid(Long operatorid) {
		this.operatorid = operatorid;
	}
	public Long getNoteid() {
		return noteid;
	}
	public void setNoteid(Long noteid) {
		this.noteid = noteid;
	}
	
	@Override
	public String toString() {
		return "HistoryItem [id=" + id + ", machineid=" + machineid + ", countdate=" + countdate + ", countamount="
				+ countamount + ", operatorid=" + operatorid + ", noteid=" + noteid + "]";
	}	

}
