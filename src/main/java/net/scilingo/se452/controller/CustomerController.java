package net.scilingo.se452.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import net.scilingo.se452.banking.BankingService;
import net.scilingo.se452.banking.Customer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CustomerController {

	@Autowired
	private BankingService bankingService;
	
	@RequestMapping(path="/customer/list",method=RequestMethod.GET)
	public void customerList(Model model) {
		List<Customer> customerList = bankingService.getAllCustomers();
		model.addAttribute("customerList", customerList);
	}
	
}
