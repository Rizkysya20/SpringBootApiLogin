package demo.project.com.controller;

import demo.project.com.dto.ApiResponse;
import demo.project.com.entity.User;
import demo.project.com.service.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody User user) {

        ApiResponse response = authService.register(user);

        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody User user) {
        ApiResponse response = authService.login(user);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }

        if (response.getMessage().equals("user not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}