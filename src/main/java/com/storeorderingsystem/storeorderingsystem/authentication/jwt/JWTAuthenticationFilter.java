package com.storeorderingsystem.storeorderingsystem.authentication.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    final private JWTGenerator tokenGenerator;
    final private UserDetailsService userDetailsService;

    public JWTAuthenticationFilter(JWTGenerator tokenGenerator, UserDetailsService userDetailsService) {
    	this.tokenGenerator = tokenGenerator;
    	this.userDetailsService = userDetailsService;	
    }

    //for ever request, if the header has a token and if the token is valid or not
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
    		throws ServletException, IOException {
    	
    	String username = null;
        String token = getJWTFromRequest(request);
        if (token != null && StringUtils.hasText(token)) {
            try {
                username = this.tokenGenerator.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                logger.info("Illegal Argument while fetching the username !!" + e.getMessage());
            } catch (ExpiredJwtException e) {
                logger.info("Given jwt token is expired !!" + e.getMessage());
            } catch (MalformedJwtException e) {
                logger.info("Some changed has done in token !! Invalid Token" + e.getMessage());
            } catch (Exception e) {
                logger.info("Exception " + e.getMessage());
            }
        } else {
            logger.info("Invalid Header Value !! ");
        }
              
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	
        	UserDetails userDetails = null;
        	try {
        		userDetails = this.userDetailsService.loadUserByUsername(username);
            } catch (UsernameNotFoundException e) {
                logger.info("User not found !!" + e.getMessage());
            } 
            
        	if(userDetails != null) {
                Boolean validateToken = this.tokenGenerator.validateToken(token, username, userDetails);
                if (validateToken) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    logger.info("Validation fails !!");
                }
        	}else {
        		 logger.info("User not found !!");
        	}
        }
        filterChain.doFilter(request, response);
    }

    private String getJWTFromRequest(HttpServletRequest request) {
        String requestHeader = request.getHeader("Authorization");
        if(requestHeader != null && StringUtils.hasText(requestHeader) && requestHeader.startsWith("Bearer ")) {
            return requestHeader.substring(7, requestHeader.length());
        }
        return null;
    }
}
