package com.zkt.zktspringjpa.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zkt.zktspringjpa.model.SystemUser;
import com.zkt.zktspringjpa.repository.SystemUserRepository;
import com.zkt.zktspringjpa.security.jwt.JwtAuthenticationFilter;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private SystemUserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("Username : " + username);

        SystemUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        logger.info("User found with username: " + username);

        System.out.println("User found with username: " + username);

        // Optional<SystemUser> userOptional = userRepository.findByUsername(username);

        // SystemUser user = userOptional.orElse(null);

        // return new
        // org.springframework.security.core.userdetails.User(user.getUsername(),
        // user.getPassword(),
        // new ArrayList<>());

        return CustomUserDetails.build(user);// spring security uses this user to authenticate the
                                             // user

    }

}
