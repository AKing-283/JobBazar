package com.aking.Job.portal.services;

import com.aking.Job.portal.entity.Users;
import com.aking.Job.portal.repository.UserRepository;
import com.aking.Job.portal.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


//Instructing Spring security how to retrieve users data from database
@Service
public class CustomerUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomerUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByEmail(username).orElseThrow(() ->  new UsernameNotFoundException("Could Not found User!!"));
        return new CustomUserDetails(users);
    }
}
