package com.mercury.FreelancerApp.controller;

import com.mercury.FreelancerApp.http.AuthenticationRequest;
import com.mercury.FreelancerApp.http.AuthenticationResponse;
import com.mercury.FreelancerApp.http.RegisterRequest;
import com.mercury.FreelancerApp.http.Response;
import com.mercury.FreelancerApp.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")

public class AuthController {
    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @GetMapping("/check-login")
    public Response checkLogin(Authentication authentication) {
        return authenticationService.checkLogin(authentication);
    }
}
