package com.wichat.service.config.exceptionhandler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

@Log4j2
@ControllerAdvice
public class CustomExceptionHandler {

    @Value("${server.debug}")
    private boolean debug;

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<?> handleHttpClientErrorException(HttpClientErrorException ex, HttpServletRequest request) {
        log.error("handleHttpClientErrorException at {}: {}", request.getServletPath(), ex.getMessage());
        String message = debug ? ex.getMessage() : HttpStatus.UNAUTHORIZED.toString();
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("code", HttpStatus.UNAUTHORIZED.value());
        body.put("message", message);
        body.put("path", request.getServletPath());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        log.error("handleAccessDeniedException at {}: {}", request.getServletPath(), ex.getMessage());
        String message = debug ? ex.getMessage() : HttpStatus.FORBIDDEN.toString();
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("code", HttpStatus.FORBIDDEN.value());
        body.put("message", message);
        body.put("path", request.getServletPath());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        log.error("handleAuthenticationException at {}: {}", request.getServletPath(), ex.getMessage());
        String message = debug ? ex.getMessage() : HttpStatus.NETWORK_AUTHENTICATION_REQUIRED.toString();
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("code", HttpStatus.NETWORK_AUTHENTICATION_REQUIRED.value());
        body.put("message", message);
        body.put("path", request.getServletPath());
        return ResponseEntity.status(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED).body(body);
    }

    @ExceptionHandler(InvocationTargetException.class)
    public ResponseEntity<?> handleInvocationTargetException(InvocationTargetException ex, HttpServletRequest request) {
        log.error("handleInvocationTargetException at {}: {}", request.getServletPath(), ex.getMessage());
        String message = debug ? ex.getMessage() : HttpStatus.INTERNAL_SERVER_ERROR.toString();
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("message", message);
        body.put("path", request.getServletPath());
        return ResponseEntity.status(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex, HttpServletRequest request) {
        log.error("handleException at {}: {}", request.getServletPath(), ex.getMessage());
        String message = debug ? ex.getMessage() : HttpStatus.BAD_REQUEST.toString();
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("code", HttpStatus.BAD_REQUEST.value());
        body.put("message", message);
        body.put("path", request.getServletPath());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
