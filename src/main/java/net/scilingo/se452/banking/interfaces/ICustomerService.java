package net.scilingo.se452.banking.interfaces;

public interface ICustomerService {

	void saveCustomer(ICustomer customer);

	void deleteCustomer(ICustomer customer);

	ICustomer createCustomer(String firstName, String middleInitial, String lastName, IAddress address);

}