package com.surya.loginTest.service;


import java.util.List;
import java.util.Optional;

import com.surya.loginTest.model.AppUser;


public interface UserDetailService {


    Optional<AppUser> getUserById(long id);

    AppUser getUserByEmail(String email);

    List<AppUser> getAllUsers();

    AppUser saveUser(AppUser user);

}
