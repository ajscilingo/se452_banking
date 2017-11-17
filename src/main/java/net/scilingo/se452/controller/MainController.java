package net.scilingo.se452.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MainController {

	@RequestMapping(value = { "/", "/hello**" }, method = RequestMethod.GET)
	public ModelAndView defaultPage() {

	  ModelAndView modelAndView = new ModelAndView();
	  modelAndView.addObject("title", "Spring Security Login Form - Database Authentication");
	  modelAndView.addObject("message", "This is default page!");
	  modelAndView.setViewName("hello");
	  return modelAndView;

	}

	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {

	  ModelAndView modelAndView = new ModelAndView();
	  modelAndView.addObject("title", "Spring Security Login Form - Database Authentication");
	  modelAndView.addObject("message", "This page is for ROLE_ADMIN only!");
	  modelAndView.setViewName("admin");
	  return modelAndView;

	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout) {

	  ModelAndView modelAndView = new ModelAndView();
	  if (error != null) {
		modelAndView.addObject("error", "Invalid username and password!");
	  }

	  if (logout != null) {
		modelAndView.addObject("msg", "You've been logged out successfully.");
	  }
	  modelAndView.setViewName("login");

	  return modelAndView;

	}

	//for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

	  ModelAndView modelAndView = new ModelAndView();

	  //check if user is login
	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	  if (!(auth instanceof AnonymousAuthenticationToken)) {
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		modelAndView.addObject("username", userDetail.getUsername());
	  }

	  modelAndView.setViewName("403");
	  return modelAndView;

	}

	// For LogoutComplete
	@RequestMapping(value = "/logoutComplete", method = RequestMethod.GET)
	public String logoutComplete(Model model) {
		model.addAttribute("title", "Logout");
		return "logoutComplete";
	}
}
