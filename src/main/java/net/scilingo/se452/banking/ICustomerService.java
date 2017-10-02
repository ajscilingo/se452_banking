package net.scilingo.se452.banking;

public interface ICustomerService {

	void saveCustomer(ICustomer customer);

	void deleteCustomer(ICustomer customer);

	void createCustomer(String firstName, String middleInitial, String lastName, IAddress address);

}