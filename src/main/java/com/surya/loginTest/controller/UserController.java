package com.surya.loginTest.controller;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.surya.loginTest.model.AppUser;
import com.surya.loginTest.model.Role;
import com.surya.loginTest.repository.RoleRepository;
import com.surya.loginTest.repository.UserRepository;
import com.surya.loginTest.service.UserDetailService;
import com.surya.loginTest.service.UserDetailServiceImpl;



@Controller
public class UserController {
	
	public static final int ID_LENGTH = 5;
	
	@Autowired
	private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
	
	@Autowired
	private UserDetailServiceImpl userService;
			
		
		@RequestMapping(value="/password", method=RequestMethod.GET)
		public String changePassword() {
			return "changepassword";
		}
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String getRegisterForm() {
		return "register";
	}

	@RequestMapping(value="/register", method=RequestMethod.POST)
	public ModelAndView saveUserDetail(AppUser user, BindingResult bindingResult) {
		ModelAndView modelView = new ModelAndView();
		AppUser userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the username provided");
        }
		if(bindingResult.hasErrors()) {
			modelView.addObject("message", "Could not Register the new User");
			modelView.setViewName("register");
		}
		else {			
		user.setId(generateUniqueId());
		userService.saveUser(user);
		modelView.setViewName("register");
		}
		
		return modelView;
		}
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value="/userlist", method=RequestMethod.GET)
	public String getUserList(Model model) {
		List<AppUser> userList = userRepository.findAll();
		System.out.println(userList);
		model.addAttribute("userList", userList);
		return "userlist";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/updateuser/{id}") //@RequestMapping(value = "/plant/edit")
	public String updateUser(Model model,@PathVariable String id){
		System.out.println("in console Id: " + id);
		Optional<AppUser> user = userRepository.findById(id);
		model.addAttribute("user", user.get());
		//model.addAttribute("addStatus", false);
		return "updateuser";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/updateuser")
	public String saveUpdatedUser(@Valid @ModelAttribute("user") AppUser user, @RequestParam("password") String password, @RequestParam("chpassword") String chpassword) {
		if(!((password == null)||(password.isEmpty()))) {
			userService.saveUser(user);
		}
		else {
			user.setPassword(chpassword);
			user.setEnabled(true);
	        Role userRole = roleRepository.findByRole("ADMIN");
	        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
			userRepository.save(user);
		}
		
		return "redirect:/userlist";
		}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value="/user/delete", method=RequestMethod.DELETE)
	public String deleteUser(@RequestParam("id") String id) {
		userRepository.deleteById(id);
		return "redirect:/userlist";
	}
	
	public String generateUniqueId() {
        return RandomStringUtils.randomAlphanumeric(ID_LENGTH);
    }
	


}
