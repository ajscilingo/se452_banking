package net.scilingo.se452.banking;

public class MongoAddress implements IAddress {

	private String addressLine1;
	private String addressLine2;
	private int zipcode;
	private String county;
	
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

}
