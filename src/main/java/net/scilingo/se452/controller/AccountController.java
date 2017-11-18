package net.scilingo.se452.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import net.scilingo.se452.banking.Account;
import net.scilingo.se452.banking.AccountType;
import net.scilingo.se452.banking.BankingService;
import net.scilingo.se452.banking.Customer;
import net.scilingo.se452.banking.Deposit;
import net.scilingo.se452.banking.Withdraw;

@Controller
@SessionAttributes({ "customer", "account" })
public class AccountController {

	@Autowired
	private BankingService bankingService;
	
	@RequestMapping(path = "/account/list", method = RequestMethod.GET)
	public ModelAndView customerAccountsList() {
		ModelAndView modelAndView = new ModelAndView();

		// check if user is logged in
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			String username = userDetail.getUsername();
			Customer customer = bankingService.getCustomerByUserName(username);
			if (customer != null) {
				Set<Account> customerAccounts = customer.getAccounts();
				modelAndView.addObject("customerAccounts", customerAccounts);
				if (!modelAndView.getModel().containsKey("customer"))
					modelAndView.addObject("customer", customer);
			}
		}

		return modelAndView;
	}

	@RequestMapping(path = "/account/view", method = RequestMethod.GET)
	public ModelAndView viewAccount(@RequestParam("id") Integer id, Model model) {
	
		ModelAndView modelAndView = new ModelAndView();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			String username = userDetail.getUsername();
			Customer customer = bankingService.getCustomerByUserName(username);
			if (customer != null) {
				if (!modelAndView.getModel().containsKey("customer"))
					modelAndView.addObject("customer", customer);
				
				Account account = new Account();
				account.setId(id);
				account = bankingService.getAccount(account);
				if(account != null) {
					
					// check to see if account belongs to customer
					if(customer.getAccounts().contains(account))
						modelAndView.addObject("account", account);
					else // if not redirect back to account list
						return new ModelAndView("redirect:/account/list");
				} else
					// redirect back to account list if account not found
					return new ModelAndView("redirect:/account/list");
			}
		}
		
		return modelAndView;
	}
	
	@RequestMapping(path = "/account/add", method = RequestMethod.GET)
	public ModelAndView accountAdd() {
	
		ModelAndView modelAndView = new ModelAndView();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			String username = userDetail.getUsername();
			Customer customer = bankingService.getCustomerByUserName(username);
			if (customer != null) {
				if (!modelAndView.getModel().containsKey("customer"))
					modelAndView.addObject("customer", customer);
			}
		}
			
		modelAndView.addObject("account", new Account());
		return modelAndView;
	}
	
	@RequestMapping(path = "/account/deposit", method = RequestMethod.POST)
	public ModelAndView accountDepositSubmit(@ModelAttribute final Deposit deposit, Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();	
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			Account account = (Account) model.asMap().get("account");
			if(account != null) {
				deposit.setAccount(account);
			}
			bankingService.makeDeposit(deposit);
		}
		return new ModelAndView("redirect:/account/list");
	}
	
	@RequestMapping(path = "/account/withdraw", method = RequestMethod.POST)
	public ModelAndView accountWithdrawSubmit(@ModelAttribute final Withdraw withdraw, Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();	
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			Account account = (Account) model.asMap().get("account");
			if(account != null) {
				withdraw.setAccount(account);
			}
			bankingService.makeWithdraw(withdraw);
		}
		return new ModelAndView("redirect:/account/list");
	}
	
	@RequestMapping(path = "/account/add", method = RequestMethod.POST)
	public String accountAddSubmit(@ModelAttribute final Account account, Model model) {
		ModelAndView modelAndView = new ModelAndView();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();	
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			if(account != null) {
				
				//AccountType accountType = account.getAccountType();
				//Defaulting to Checking
				AccountType accountType = account.getAccountType();
				UserDetails userDetail = (UserDetails) auth.getPrincipal();
				String username = userDetail.getUsername();
				Customer customer = bankingService.getCustomerByUserName(username);
				
				if(customer != null) {
				
					switch(accountType) {
					case CHECKING :
						bankingService.createNewCheckingAccount(customer, account.getBalance());
						break;
					case CD :
						bankingService.createNewCDAccount(customer, account.getBalance());
						break;
					case SAVINGS :
						bankingService.createNewSavingsAccount(customer, account.getBalance());
						break;
					default:
						bankingService.createNewCheckingAccount(customer, account.getBalance());
						break;
					}
				}
				modelAndView.addObject("account", account);
			}
		}
		return "redirect:/account/list";
	}
	
}
