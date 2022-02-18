package com.infinity.ProductiveIO.dailyHistory.model;

public class HistoryItemView {
	
	private long id;
	private int machineid;
	private String machinename;
	private java.sql.Date countdate;
	private int countamount;
	private String operatorname;
	private String note;
	private int average;
	private double inactive;
	private double offline;
	
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
	public int getCountamount() {
		return countamount;
	}
	public void setCountamount(int countamount) {
		this.countamount = countamount;
	}
	public String getOperatorname() {
		return operatorname;
	}
	public void setOperatorname(String operatorname) {
		this.operatorname = operatorname;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}	
	public int getAverage() {
		return average;
	}
	public void setAverage(int average) {
		this.average = average;
	}
	public double getInactive() {
		return inactive;
	}
	public void setInactive(double inactive) {
		this.inactive = inactive;
	}
	public double getOffline() {
		return offline;
	}
	public void setOffline(double offline) {
		this.offline = offline;
	}
	
	@Override
	public String toString() {
		return "HistoryItemView [id=" + id + ", machineid=" + machineid + ", machinename=" + machinename
				+ ", countdate=" + countdate + ", countamount=" + countamount + ", operatorname=" + operatorname
				+ ", note=" + note + ", average=" + average + ", inactive=" + inactive + ", offline=" + offline + "]";
	}

}
