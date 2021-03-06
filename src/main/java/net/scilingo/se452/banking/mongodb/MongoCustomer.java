package net.scilingo.se452.banking.mongodb;

import net.scilingo.se452.banking.interfaces.IAddress;
import net.scilingo.se452.banking.interfaces.ICustomer;

public class MongoCustomer implements ICustomer {

	private Integer id;
	private String firstName;
	private String middleInitial;
	private String lastName;
	private MongoAddress address;
	
	@Override
	public Integer getId() {
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
	public void setId(Integer id) {
		this.id = id;
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


	public MongoAddress getAddress() {
		return address;
	}


	public void setAddress(MongoAddress address) {
		this.address = (MongoAddress) address;
	}

	

}
