package com.storeorderingsystem.storeorderingsystem.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import com.storeorderingsystem.storeorderingsystem.model.Role;
import com.storeorderingsystem.storeorderingsystem.repository.UserRepository;

@Service
public class CustomUserDetailsService /* implements UserDetailsService */ {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public CustomUserDetailsService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
    	this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }
    
	@Bean
	public UserDetailsService users() {
    //@Override
    //public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
    	
        ArrayList<GrantedAuthority> roles = new ArrayList<GrantedAuthority>(); 
        roles.add(new SimpleGrantedAuthority("ADMIN")); 
        roles.add(new SimpleGrantedAuthority("USER")); 
        
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
    	//com.storeorderingsystem.storeorderingsystem.model.User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));		
    	return new InMemoryUserDetailsManager(inMemoryAdmin, inMemoryUser);
//    	return new User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
    	
    	Collection<GrantedAuthority> coll = roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}