package net.scilingo.se452.banking;

public class MongoAddress implements IAddress, IAddressInfo {

	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String county;
	private int zipcode;
	
	@Override
	public String getAddressLine1() {
		return addressLine1;
	}

	@Override
	public String getAddressLine2() {
		return addressLine2;
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
	public int getZipcode() {
		return zipcode;
	}

	@Override
	public String getCounty() {
		return county;
	}

	@Override
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	@Override
	public void setCounty(String county) {
		this.county = county;
	}

	@Override
	public String getCity() {
		return city;
	}

	@Override
	public String getState() {
		return state;
	}

	@Override
	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public void setState(String state) {
		this.state = state;
	}
	
	
}
