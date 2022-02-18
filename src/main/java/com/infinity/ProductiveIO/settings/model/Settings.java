package com.infinity.ProductiveIO.settings.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="settings")
public class Settings {
	
	private long id;
	private String settingkey;
	private String settingvalue;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSettingkey() {
		return settingkey;
	}
	public void setSettingkey(String settingkey) {
		this.settingkey = settingkey;
	}
	public String getSettingvalue() {
		return settingvalue;
	}
	public void setSettingvalue(String settingvalue) {
		this.settingvalue = settingvalue;
	}
	
	

}
