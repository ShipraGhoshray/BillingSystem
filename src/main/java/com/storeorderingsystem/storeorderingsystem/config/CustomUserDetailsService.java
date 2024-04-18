package com.storeorderingsystem.storeorderingsystem.config;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.storeorderingsystem.storeorderingsystem.model.Role;
import com.storeorderingsystem.storeorderingsystem.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public CustomUserDetailsService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
    	this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }
    
	/*@Bean
	public UserDetailsService users() {        
    	UserDetails inMemoryAdmin = User.builder()
				.username("joey")
				.password(passwordEncoder.encode("password"))
				.roles("ADMIN", "USER")
				.build();
		UserDetails inMemoryUser = User.builder()
				.username("rachel")
				.password(passwordEncoder.encode("password"))
				.roles("USER")
				.build();
    	return new InMemoryUserDetailsManager(inMemoryAdmin, inMemoryUser);
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        	
      	com.storeorderingsystem.storeorderingsystem.model.User user = 
      			userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
      	Collection<GrantedAuthority> grantedAuthorities = mapRolesToAuthorities(user.getRoles());
      	log.info("User: " + user.getUsername() + " --- Authorities : " + grantedAuthorities);
      	return new User(user.getUsername(), passwordEncoder.encode(user.getPassword()), grantedAuthorities);
    }
    	
    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
    	
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().trim())).collect(Collectors.toList());
    }
    
}