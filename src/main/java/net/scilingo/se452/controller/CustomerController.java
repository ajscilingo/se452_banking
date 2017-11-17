package net.scilingo.se452.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import net.scilingo.se452.banking.BankingService;
import net.scilingo.se452.banking.Customer;
import net.scilingo.se452.banking.UserCustomer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes({ "customer" })
public class CustomerController {

	@Autowired
	private BankingService bankingService;

	@RequestMapping(path = "/customer/list", method = RequestMethod.GET)
	public void customerList(Model model) {
		List<Customer> customerList = bankingService.getAllCustomers();
		model.addAttribute("customerList", customerList);
	}

	@ModelAttribute("allZipcodes")
	public List<Integer> populateZipcodes() {
		List<Integer> zipcodes = bankingService.getAllZipcodes();
		return zipcodes;
	}

	@ModelAttribute("allCounties")
	public List<String> populateCounties() {
		List<String> countyNames = bankingService.getAllCounties();
		return countyNames;
	}

	@RequestMapping(path = "/customer/add", method = RequestMethod.GET)
	public ModelAndView customerAdd() {

		ModelAndView modelAndView = new ModelAndView();

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			String username = userDetail.getUsername();
			Customer customer = bankingService.getCustomerByUserName(username);
			if(customer != null)
				modelAndView.addObject("customer", customer);
			else
				modelAndView.addObject("customer", new Customer());
		}
		else {
			modelAndView.addObject("customer", new Customer());
		}
		//return new ModelAndView("/customer/add", "customer", new Customer());
		return modelAndView;
	}

	@RequestMapping(path = "/customer/add", method = RequestMethod.POST)
	public ModelAndView customerAddSubmit(@ModelAttribute final Customer customer) {
		ModelAndView modelAndView = new ModelAndView();
		bankingService.createNewCustomer(customer);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			String username = userDetail.getUsername();
			
			UserCustomer userCustomer = new UserCustomer();
			userCustomer.setUsername(username);
			userCustomer.setCustomer(customer);
			bankingService.createNewUserCustomer(userCustomer);
		}
		
		modelAndView.addObject("customer", customer);
		return modelAndView;
	}
}
