package net.scilingo.se452.banking;



public class MongoCustomer implements ICustomer {

	private int id;
	private String firstName;
	private String middleInitial;
	private String lastName;
	private MongoAddress address;
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public String getMiddleInitial() {
		return middleInitial;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;

	}

	@Override
	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public IAddress getAddress() {
		return address;
	}

	@Override
	public void setAddress(IAddress address) {
		this.address = (MongoAddress) address;
	}

	

}
