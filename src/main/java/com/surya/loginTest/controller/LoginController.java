package com.surya.loginTest.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class LoginController {
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String getloginForm() {
		return "login";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String getRegisterForm() {
		return "register";
	}
	
	@RequestMapping(value="/default", method=RequestMethod.GET)
	public String getDefaultForm() {
		return "layouts/default";
	}
	
	@RequestMapping(value="/plantdetails", method=RequestMethod.GET)
	public String gethomepage() {
		return "plantdetails";
	}
	
	@GetMapping("/form") //@RequestMapping(value = "/layouts/homepage")
	public String userAddPage(Model model){
		model.addAttribute("addStatus", true);
		
		return "plantform";
	}
	@RequestMapping(value="/viewallplant", method=RequestMethod.GET)
	public String getallplant() {
		return "viewallplant";
	}
	
}
