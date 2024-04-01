package com.storeorderingsystem.storeorderingsystem.authentication;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.storeorderingsystem.storeorderingsystem.util.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTGenerator {
		
	public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
    	return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<String, Object>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {
    	return Jwts.builder()
    			.setClaims(claims)
    			.setSubject(username)
    			.setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(getTokenExpirationTime())
                .signWith(key())	                //.signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SecurityConstants.SECRET_KEY));
    }
    
    private Date getTokenExpirationTime() {
    	return new Date(System.currentTimeMillis() + SecurityConstants.JWT_EXPIRATION * 1000);
    }
    
	public Boolean validateToken(String token, String username, UserDetails userDetails) {
        //final String username = getUsernameFromToken(token);
        Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
	
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}