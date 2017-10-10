package net.scilingo.se452.banking;

import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;

import org.mockito.invocation.InvocationOnMock;


public class CustomerIdSetter implements Answer<Void> {

	private int customerId;
	
	public CustomerIdSetter(int customerId) {
		this.customerId = customerId;
	}

	@Override
	public Void answer(InvocationOnMock invocation) throws Throwable {
		assertEquals(1, invocation.getArguments().length);
		Customer customer = invocation.getArgumentAt(0, Customer.class);
		customer.setId(customerId);
		return null;
	}
	
	
}
