package com.storeorderingsystem.storeorderingsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.storeorderingsystem.storeorderingsystem.authentication.JWTAuthenticationFilter;
import com.storeorderingsystem.storeorderingsystem.authentication.JwtAuthenticationEntryPoint;


@Configuration
@EnableWebSecurity(debug=true)
public class SecurityConfiguration {

    private JwtAuthenticationEntryPoint authEntryPoint;
    private JWTAuthenticationFilter jwtAuthFilter;

	public SecurityConfiguration(JwtAuthenticationEntryPoint authEntryPoint, JWTAuthenticationFilter jwtAuthFilter) {
        this.authEntryPoint = authEntryPoint;
        this.jwtAuthFilter = jwtAuthFilter;
    }

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception { 
		//SecurityFilterChain class create a chain of filters.
		http.csrf(csrf -> csrf.disable())
			.cors(cors -> cors.disable())
			.authorizeHttpRequests(authorize -> { authorize
				.requestMatchers(HttpMethod.POST, "/auth/products").hasRole("ADMIN")
				.requestMatchers(HttpMethod.GET, "/auth/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/auth/users").permitAll()
         		.requestMatchers("/auth/login").permitAll()
				.anyRequest().authenticated();
			}).httpBasic(Customizer.withDefaults());
		
		http.exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPoint));
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
		
		/*	authorize.requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN");
		 *  authorize.requestMatchers(HttpMethod.PATCH, "/api/**").hasAnyRole("ADMIN");
		 *  authorize.requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN");         		
       		authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
		 */
	}
}
