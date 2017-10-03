package net.scilingo.se452.banking;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity @IdClass(AddressInfoId.class)
@Table(name = "ADDRESS_INFO")
public class AddressInfo implements IAddressInfo, Serializable {

	private static final long serialVersionUID = -5661826697467263158L;
	
	@Id
	@Column(name="ZIPCODE", nullable = false)
	private int zipcode;
	
	@Id
	@Column(name="COUNTY", nullable = false)
	private String county;
	
	@Column(name="CITY")
	private String city;
	
	@Column(name="STATE")
	private String state;
	
	public int getZipcode() {
		return zipcode;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCounty() {
		return county;
	}	
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
}
