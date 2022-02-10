package com.infinity.ProductiveIO.totalDashboard.model;

public class TotalDashboardItem {
	
	private long id;
	private String machineName;
	private java.sql.Time lastUpdate;
	private int totalForDay;
	private int lastUpdateAmount;
	private int average;
	private int margin;
	private int productionPersentage;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	public java.sql.Time getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(java.sql.Time lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public int getTotalForDay() {
		return totalForDay;
	}
	public void setTotalForDay(int totalForDay) {
		this.totalForDay = totalForDay;
	}
	public int getAverage() {
		return average;
	}
	public void setAverage(int average) {
		this.average = average;
	}
	public int getMargin() {
		return margin;
	}
	public void setMargin(int margin) {
		this.margin = margin;
	}
	public int getProductionPersentage() {
		return productionPersentage;
	}
	public void setProductionPersentage(int productionPersentage) {
		this.productionPersentage = productionPersentage;
	}
	public int getLastUpdateAmount() {
		return lastUpdateAmount;
	}
	public void setLastUpdateAmount(int lastUpdateAmount) {
		this.lastUpdateAmount = lastUpdateAmount;
	}
	

}
