package com.surya.loginTest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebUserController {
	
	@RequestMapping(value="/webuser", method=RequestMethod.GET)
	public String changePassword() {
		return "webusers/homepage";
	}
	
	@RequestMapping(value="/showmatch", method=RequestMethod.GET)
	public String showMatchPlant() {
		return "webusers/showmatch";
	}

	@RequestMapping(value="/singleview", method=RequestMethod.GET)
	public String showSinglePlant() {
		return "webusers/singleview";
	}

}
