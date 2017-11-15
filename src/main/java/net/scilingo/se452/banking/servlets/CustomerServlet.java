package net.scilingo.se452.banking.servlets;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.StringUtils;

import net.scilingo.se452.banking.Address;
import net.scilingo.se452.banking.BankingService;
import net.scilingo.se452.banking.Customer;
import net.scilingo.se452.banking.interfaces.ICustomer;

public class CustomerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2754166667771638585L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		BankingService bankingService = new BankingService();
		
		ICustomer customer;
		
		ServletContext context = getServletContext();
		
		String id = req.getParameter("id");
		Address address = new Address();
		String firstName = req.getParameter("firstName");
		String middleInitial = req.getParameter("middleInitial");
		String lastName = req.getParameter("lastName");
		address.setAddressLine1(req.getParameter("addressLine1"));
		address.setAddressLine2(req.getParameter("addressLine2"));
		address.setCounty(req.getParameter("county"));
		String zipcode = req.getParameter("zipcode");
		
		if(!StringUtils.isNullOrEmpty(zipcode)) {
			address.setZipcode(Integer.parseInt(zipcode));
		}
		if(StringUtils.isNullOrEmpty(id)) {
			customer = bankingService.createNewCustomer(firstName, middleInitial, lastName, address);
		}
		else {
			customer = bankingService.getCustomer(id);
			customer.setAddress(address);
			customer.setFirstName(firstName);
			customer.setMiddleInitial(middleInitial);
			customer.setLastName(lastName);
			bankingService.updateCustomer((Customer)customer);
		}
		
		context.setAttribute("customer", customer);
		context.setAttribute("address", customer.getAddress());
		context.getRequestDispatcher("/customer.jsp").forward(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}
	
}
