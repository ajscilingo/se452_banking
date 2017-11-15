package net.scilingo.se452.banking.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.StringUtils;

import net.scilingo.se452.banking.AccountType;
import net.scilingo.se452.banking.BankingService;
import net.scilingo.se452.banking.Customer;

public class AccountServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2950189259357240505L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Customer customer = (Customer) getServletContext().getAttribute("customer");
		String startingBalance = req.getParameter("startingBalance");
		
		if(!StringUtils.isNullOrEmpty(startingBalance) && customer != null) {
			
			String strAccountType = req.getParameter("accountType");
			BankingService bankingService = new BankingService();
			AccountType accountType = AccountType.valueOf(strAccountType);
			
			int intStartingBalance = Integer.parseInt(startingBalance);
			
			switch(accountType) {
				case CHECKING :
					bankingService.createNewCheckingAccount(customer, intStartingBalance);
					break;
				case CD :
					bankingService.createNewCDAccount(customer, intStartingBalance);
					break;
				case SAVINGS :
					bankingService.createNewSavingsAccount(customer, intStartingBalance);
					break;
				default:
					bankingService.createNewCheckingAccount(customer, intStartingBalance);
					break;
			}
		}
		
		getServletContext().getRequestDispatcher("/account.jsp").forward(req, resp);
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	
	
}
