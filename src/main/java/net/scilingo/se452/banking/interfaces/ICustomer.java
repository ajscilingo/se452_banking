package net.scilingo.se452.banking.interfaces;

public interface ICustomer {

	public Integer getId();
	public String getFirstName();
	public String getMiddleInitial();
	public String getLastName();
	public IAddress getAddress();
	
	public void setId(Integer id);
	public void setFirstName(String firstName);
	public void setMiddleInitial(String middleInitial);
	public void setLastName(String lastName);
	public void setAddress(IAddress address);
	
	
}
