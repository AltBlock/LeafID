package com.surya.loginTest.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.surya.loginTest.model.AppUser;
import com.surya.loginTest.model.Role;
import com.surya.loginTest.repository.RoleRepository;
import com.surya.loginTest.repository.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailService {

    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
	@Autowired
    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

   // private BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder();

    public AppUser findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    

	@Override
	public Optional<AppUser> getUserById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AppUser getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}


	@Override
	public List<AppUser> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}
    
	@Override
    public AppUser saveUser(AppUser user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(user);
        return userRepository.save(user);
    }
}