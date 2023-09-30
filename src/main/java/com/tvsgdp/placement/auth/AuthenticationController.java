package com.tvsgdp.placement.auth;


import com.tvsgdp.placement.config.ResponseHandler;
import com.tvsgdp.placement.exception.InvalidCredentialsException;
import com.tvsgdp.placement.exception.UsernameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request){
        try {
            AuthenticationResponse response = authenticationService.register(request);
            return ResponseHandler.generateResponse("User registration successful", HttpStatus.CREATED, response);
        } catch (UsernameAlreadyExistsException e) {
            return ResponseHandler.generateResponse("Username already exists", HttpStatus.CONFLICT,null);
        }
    }
    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(@RequestBody AuthenticationRequest request){
        try {
            AuthenticationResponse response = authenticationService.authenticate(request);
            return ResponseHandler.generateResponse("User authentication successful", HttpStatus.OK, response);
        } catch (InvalidCredentialsException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.UNAUTHORIZED,null);
        }
    }
}
