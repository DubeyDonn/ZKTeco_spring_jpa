package com.zkt.zktspringjpa.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zkt.zktspringjpa.model.dto.AuthDTO;
import com.zkt.zktspringjpa.model.dto.SystemUserDTO;
import com.zkt.zktspringjpa.model.request.AuthRequest;
import com.zkt.zktspringjpa.model.response.Response;
import com.zkt.zktspringjpa.repository.SystemUserRepository;
import com.zkt.zktspringjpa.security.jwt.JwtHelper;
import com.zkt.zktspringjpa.security.service.CustomUserDetails;
import com.zkt.zktspringjpa.security.service.CustomUserDetailsService;

@RestController
@CrossOrigin
public class AuthController {

    @Autowired
    SystemUserRepository userRepository;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<Response<AuthDTO>> login(@RequestBody AuthRequest request) {

        this.doAuthenticate(request.getUsername(), request.getPassword());

        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService
                .loadUserByUsername(request.getUsername());

        String token = this.jwtHelper.generateToken(userDetails); // generating token

        SystemUserDTO user = SystemUserDTO.builder().id(userDetails.getId()).name(userDetails.getName())
                .username(userDetails.getUsername()).roles(userDetails.getAuthorities()).build();

        AuthDTO data = AuthDTO.builder().token(token).user(user).build();

        Response<AuthDTO> response = Response.<AuthDTO>builder().success(true).message("Login Successful").data(data)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password !!");
        }
    }
}
