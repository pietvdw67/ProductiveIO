package com.infinity.ProductiveIO.operator.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="operator")
public class OperatorItem {
	
	private long id;
	private String operatorname;
	private Long machineid;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOperatorname() {
		return operatorname;
	}
	public void setOperatorname(String operatorname) {
		this.operatorname = operatorname;
	}
	public Long getMachineid() {
		return machineid;
	}
	public void setMachineid(Long machineid) {
		this.machineid = machineid;
	}
	@Override
	public String toString() {
		return "OperatorItem [id=" + id + ", operatorname=" + operatorname + ", machineid=" + machineid + "]";
	}
	

}
