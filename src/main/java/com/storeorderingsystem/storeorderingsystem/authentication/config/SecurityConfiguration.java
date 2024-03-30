package com.storeorderingsystem.storeorderingsystem.authentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.storeorderingsystem.storeorderingsystem.authentication.jwt.JWTAuthenticationFilter;
import com.storeorderingsystem.storeorderingsystem.authentication.jwt.JwtAuthenticationEntryPoint;


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
				.requestMatchers("/api/products").hasRole("ADMIN")
				//.requestMatchers(HttpMethod.DELETE, "/auth/users/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.GET, "/auth/**").permitAll()
				//.requestMatchers(HttpMethod.GET, "/auth/users/**").permitAll()
				//.requestMatchers(HttpMethod.GET, "/api/products").permitAll()
				//.requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
         		.requestMatchers("/auth/login").permitAll() //working 1
         		//.requestMatchers("/auth/*").permitAll()
				.anyRequest().authenticated();	//working 1
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
