package com.infinity.ProductiveIO.model;

public class ItemDetailView {
	private long id;
	private int machineid;
	private String machinename;
	private java.sql.Date countdate;
	private java.sql.Time counttime;
	private int countamount;
	
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
	public String getMachinename() {
		return machinename;
	}
	public void setMachinename(String machinename) {
		this.machinename = machinename;
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
	
	

}
