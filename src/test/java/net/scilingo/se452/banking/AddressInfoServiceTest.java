package net.scilingo.se452.banking;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AddressInfoService.class})
public class AddressInfoServiceTest extends Stubtest {
	
	private AddressInfoService addressInfoService;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		addressInfoService = new AddressInfoService(entityManager);
	}
	
	@Test
	public void FindAddressInfo1(){
		
		AddressInfoId primaryKey = new AddressInfoId(60521, "Dupage");
		AddressInfo response = buildMockAddressInfo("Hinsdale", "Illinois", "Dupage", 60521);
		when(entityManager.find(AddressInfo.class, primaryKey)).thenReturn(response);
		Customer mockHinsdaleCustomer = buildHinsdaleDupageCustomer();
		AddressInfo mockedCall = addressInfoService.getAddressInfo(mockHinsdaleCustomer);
		verify(entityManager, times(1)).find(AddressInfo.class, primaryKey);
		assertEquals("Hinsdale", mockedCall.getCity());
		assertEquals("Illinois", mockedCall.getState());
	}
	
	
}
