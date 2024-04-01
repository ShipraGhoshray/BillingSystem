package com.storeorderingsystem.storeorderingsystem.controller;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.storeorderingsystem.storeorderingsystem.authentication.JWTGenerator;
import com.storeorderingsystem.storeorderingsystem.dto.JwtLoginRequest;
import com.storeorderingsystem.storeorderingsystem.dto.JwtLoginResponse;
import com.storeorderingsystem.storeorderingsystem.dto.ResponseHandler;
import com.storeorderingsystem.storeorderingsystem.exceptions.ResourceNotFoundException;
import com.storeorderingsystem.storeorderingsystem.model.User;
import com.storeorderingsystem.storeorderingsystem.products.dto.ProductsDto;
import com.storeorderingsystem.storeorderingsystem.products.model.Products;
import com.storeorderingsystem.storeorderingsystem.products.service.ProductsService;
import com.storeorderingsystem.storeorderingsystem.service.UserService;
import com.storeorderingsystem.storeorderingsystem.util.Constants;

@RestController
@RequestMapping("/auth")
public class AuthController {

	Logger log = LoggerFactory.getLogger(AuthController.class);
	
	private final UserService userService;
	private final ProductsService productService;
	final private UserDetailsService userDetailService;
	final private AuthenticationManager authManager;
	final private JWTGenerator jwtTokenGenerator;
	
	public AuthController(UserService userService, ProductsService productService,
			UserDetailsService userDetailService, AuthenticationManager authManager, JWTGenerator jwtTokenGenerator) {
		this.userService = userService;
		this.productService = productService;
		this.userDetailService = userDetailService;
		this.authManager = authManager;
		this.jwtTokenGenerator = jwtTokenGenerator;
	}
	
	/* LOGIN */
	@PostMapping("/login")
    public ResponseEntity<JwtLoginResponse> login(@RequestBody JwtLoginRequest request) {

        this.doAuthenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = userDetailService.loadUserByUsername(request.getUsername());
        String token = this.jwtTokenGenerator.generateToken(userDetails);
        
        //JwtResponse response = JwtResponse.builder().jwtToken(token).username(userDetails.getUsername()).build();
        JwtLoginResponse response = new JwtLoginResponse();
        response.setJwtToken(token);
        response.setUsername(userDetails.getUsername());
        return ResponseHandler.generateResponse(HttpStatus.OK, response);
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
    
    /* USERS */
    @GetMapping("/users")
    public ResponseEntity<?> getUsers(){
    	List<User> userList = (List<User>) this.userService.lookup();
    	log.info("User List size:" + userList.size());
    	if(userList != null && userList.size() > 0) {
    		return ResponseHandler.generateResponse(Constants.RESPONSE_MSG_SUCCESS, HttpStatus.OK, userList, userList.size());
    	}else {
    		return ResponseHandler.generateResponse(Constants.RESPONSE_MSG_NO_DATA_FOUND, HttpStatus.OK, null, 0);
    	}
    }

    @GetMapping("/users/{userID}")
    public ResponseEntity<?> getUserById(@Validated @PathVariable("userID")  String userIdReq){
    	Optional<User> user = userService.findById(Long.valueOf(userIdReq));
    	if(user != null) {
    		return ResponseHandler.generateResponse(Constants.RESPONSE_MSG_SUCCESS, HttpStatus.OK, user, 0);
    	}else {
    		return new ResponseEntity<String>(Constants.RESPONSE_MSG_NO_DATA_FOUND, HttpStatus.OK);
    	}
    }

    @GetMapping("/current-user")
	public ResponseEntity<String> getLoggedInUser(Principal principal){
    	return ResponseHandler.generateResponse(HttpStatus.OK, principal.getName());
    }
    
    @DeleteMapping("users/{userId}")
    public ResponseEntity<String> delete(@PathVariable(value = "userId") String userId) {
    	try {
    		User user = verifyUser(userId);
    		userService.delete(user);
    		return ResponseHandler.generateResponse( HttpStatus.OK,Constants.RESPONSE_MSG_SUCCESS);
    	}catch(NoSuchElementException e) {
    		throw new ResourceNotFoundException("User does not exist!");
    	}
    }

    private User verifyUser(String userIdReq) throws NoSuchElementException {
    	long userId = Long.valueOf(userIdReq);
    	return userService.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Delete request for user (" + userId + ")"));
    }
    
    //testing
    @PostMapping("/products")
    public ResponseEntity<String> createNewProduct(@RequestBody ProductsDto productReq){
        Products product = this.productService.createProducts(productReq.getName(), productReq.getPrice(), productReq .getType());
        if(product != null) {
            return ResponseHandler.generateResponse(HttpStatus.OK, Constants.RESPONSE_PRODUCT_CREATED);
        }else {
            return ResponseHandler.generateResponse(HttpStatus.OK, Constants.RESPONSE_PRODUCT_CREATED_FAILED);
        }
    }
}
