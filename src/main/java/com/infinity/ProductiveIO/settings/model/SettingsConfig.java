package com.infinity.ProductiveIO.settings.model;

public class SettingsConfig {
		
	private double hoursMonday;
	private double hoursTuesday;
	private double hoursWednesday;
	private double hoursThursday;
	private double hoursFriday;
	private double hoursSaturday;
	private double hoursSunday;
	
	public double getHoursMonday() {
		return hoursMonday;
	}
	public void setHoursMonday(double hoursMonday) {
		this.hoursMonday = hoursMonday;
	}
	public double getHoursTuesday() {
		return hoursTuesday;
	}
	public void setHoursTuesday(double hoursTuesday) {
		this.hoursTuesday = hoursTuesday;
	}
	public double getHoursWednesday() {
		return hoursWednesday;
	}
	public void setHoursWednesday(double hoursWednesday) {
		this.hoursWednesday = hoursWednesday;
	}
	public double getHoursThursday() {
		return hoursThursday;
	}
	public void setHoursThursday(double hoursThursday) {
		this.hoursThursday = hoursThursday;
	}
	public double getHoursFriday() {
		return hoursFriday;
	}
	public void setHoursFriday(double hoursFriday) {
		this.hoursFriday = hoursFriday;
	}
	public double getHoursSaturday() {
		return hoursSaturday;
	}
	public void setHoursSaturday(double hoursSaturday) {
		this.hoursSaturday = hoursSaturday;
	}
	public double getHoursSunday() {
		return hoursSunday;
	}
	public void setHoursSunday(double hoursSunday) {
		this.hoursSunday = hoursSunday;
	}
	
	@Override
	public String toString() {
		return "SettingsConfig [hoursMonday=" + hoursMonday + ", hoursTuesday=" + hoursTuesday + ", hoursWednesday="
				+ hoursWednesday + ", hoursThursday=" + hoursThursday + ", hoursFriday=" + hoursFriday
				+ ", hoursSaturday=" + hoursSaturday + ", hoursSunday=" + hoursSunday + "]";
	}
	
}
