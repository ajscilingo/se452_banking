package net.scilingo.se452.banking.interfaces;

public interface IAddress {

	public String getAddressLine1();
	public String getAddressLine2();
	public Integer getZipcode();
	public String getCounty();
	public void setAddressLine1(String addressLine1);
	public void setAddressLine2(String addressLine2);
	public void setZipcode(Integer zipcode);
	public void setCounty(String county);
	
}
