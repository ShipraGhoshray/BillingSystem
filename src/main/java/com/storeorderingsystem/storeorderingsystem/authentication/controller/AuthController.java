package com.storeorderingsystem.storeorderingsystem.authentication.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.storeorderingsystem.storeorderingsystem.authentication.dto.JwtLoginRequest;
import com.storeorderingsystem.storeorderingsystem.authentication.dto.JwtLoginResponse;
import com.storeorderingsystem.storeorderingsystem.authentication.jwt.JWTGenerator;

@RestController
@RequestMapping("/auth")
public class AuthController {

	final private UserDetailsService userDetailService;
	final private AuthenticationManager authManager;
	final private JWTGenerator jwtTokenGenerator;
	
	public AuthController(UserDetailsService userDetailService, AuthenticationManager authManager, JWTGenerator jwtTokenGenerator) {
		this.userDetailService = userDetailService;
		this.authManager = authManager;
		this.jwtTokenGenerator = jwtTokenGenerator;
	}
	
	@PostMapping("/login")
    public ResponseEntity<JwtLoginResponse> login(@RequestBody JwtLoginRequest request) {

        this.doAuthenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = userDetailService.loadUserByUsername(request.getUsername());
        String token = this.jwtTokenGenerator.generateToken(userDetails);
        
        //JwtResponse response = JwtResponse.builder().jwtToken(token).username(userDetails.getUsername()).build();
        JwtLoginResponse response = new JwtLoginResponse();
        response.setJwtToken(token);
        response.setUsername(userDetails.getUsername());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

	private void doAuthenticate(String email, String password) {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
		try {
			authManager.authenticate(authentication);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException(" Invalid Username or Password  !!");
		}
	}

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
    
    @GetMapping("/current-user")
	public ResponseEntity<String> getLoggedInUser(Principal principal){
        return new ResponseEntity<String>(principal.getName(), HttpStatus.OK);
    }
}
