package com.storeorderingsystem.storeorderingsystem.authentication.dto;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {

	public static ResponseEntity<JwtLoginResponse> generateResponse(HttpStatus status, JwtLoginResponse responseObj) {
		return new ResponseEntity<JwtLoginResponse>(responseObj, status);
	}
	
	public static ResponseEntity<String> generateResponse(HttpStatus status, String message) {
		return new ResponseEntity<String>(message, status);
	}
	
	public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj, int count) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", message);
		map.put("status", status.value());
		map.put("data", responseObj);
		map.put("Total Character Count", count);
		return new ResponseEntity<Object>(map, status);
	}
}
