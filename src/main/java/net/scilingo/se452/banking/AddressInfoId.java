package net.scilingo.se452.banking;

import java.io.Serializable;

public class AddressInfoId implements Serializable{

	private static final long serialVersionUID = 7765594976832577389L;
	
	private Integer zipcode;
	private String county;
	
	public AddressInfoId() {
		
	}
	
	public AddressInfoId(Integer zipcode, String county) {
		this.zipcode = zipcode;
		this.county = county;
	}
	
	public int getZipcode() {
		return zipcode;
	}
	
	
	public String getCounty() {
		return county;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result 
				+ ((county == null) ? 0 : county.hashCode());
		result = prime * result + zipcode;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		
		AddressInfoId other = (AddressInfoId) obj;
		
		if(county == null) {
			if(other.county != null)
				return false;
		}
		else if(!county.equals(other.county)) 
			return false;
		
		if(!zipcode.equals(other.zipcode))
			return false;
		
		return true;
	}
	
	
}
