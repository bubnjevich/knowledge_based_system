package com.ftn.sbnz.service.services.implementation;

import com.ftn.sbnz.model.User;
import com.ftn.sbnz.service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         //Implement the logic to load the user from the database or other source
         //Example:
         User user = userRepository.findByEmail(username).orElse(null);
         if (user == null) {
             throw new UsernameNotFoundException("User not found with username: " + username);
         }
         return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
