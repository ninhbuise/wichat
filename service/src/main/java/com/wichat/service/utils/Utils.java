package com.wichat.service.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static final String RESPONSE_CODE = "status";
    public static final String RESPONSE_MESSAGE = "message";
    public static final String RESPONSE_DATA = "body";

    public static ResponseEntity<?> appendResponse(HttpStatus status, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put(RESPONSE_CODE, status.value());
        response.put(RESPONSE_MESSAGE, message);
        response.put(RESPONSE_DATA, data);
        return new ResponseEntity<>(response, status);
    }
}
