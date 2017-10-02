package net.scilingo.se452.banking;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

@Embeddable
public class Address implements IAddress, Serializable {

	private static final long serialVersionUID = 4772479468945297846L;

	@Column(name="ADDRESS_LINE1")
	private String addressLine1;
	
	@Column(name="ADDRESS_LINE2")
	private String addressLine2;
	
	@Column(name="ZIPCODE")
	private int zipcode;
	
	@Column(name="COUNTY")
	private String county;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumns( value = {@JoinColumn(referencedColumnName = "zipcode", name="ZIPCODE", updatable = false, insertable = false),
				 @JoinColumn(referencedColumnName = "county", name="COUNTY", updatable = false, insertable = false)})
	private AddressInfo addressInfo;
	
	@Override
	public String getAddressLine1() {
		return addressLine1;
	}

	@Override
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	
	@Override
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	
	@Override
	public String getAddressLine2() {
		return addressLine2;
	}
	
	@Override
	public int getZipcode() {
		return zipcode;
	}
	
	@Override
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}
	
	@Override
	public String getCounty() {
		return county;
	}
	
	@Override
	public void setCounty(String county) {
		this.county = county;
	}
	
	public AddressInfo getAddressInfo() {
		return addressInfo;
	}

	public void setAddressInfo(AddressInfo addressInfo) {
		this.addressInfo = addressInfo;
	}

	@Override
	public String toString() {
		return addressLine1.toString() + " " + addressLine2.toString() + " " + addressInfo.getCity() + ", " + addressInfo.getState() + " " + zipcode;
	}
	

}
