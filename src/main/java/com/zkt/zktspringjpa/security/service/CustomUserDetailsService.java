package com.zkt.zktspringjpa.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zkt.zktspringjpa.model.TableUser;
import com.zkt.zktspringjpa.repository.TableUserRepository;
import com.zkt.zktspringjpa.security.jwt.JwtAuthenticationFilter;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private TableUserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        TableUser tableUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        logger.info("User found with username: {}", username);

        return CustomUserDetails.build(tableUser);// spring security uses this user to authenticate the
                                                  // user

    }

}
