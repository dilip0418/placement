package com.tvsgdp.placement.auth;
import com.tvsgdp.placement.config.JwtService;
import com.tvsgdp.placement.exception.UsernameAlreadyExistsException;
import com.tvsgdp.placement.user.User;
import com.tvsgdp.placement.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /*
    this is the register service method.
     */
    public AuthenticationResponse register(RegisterRequest request) throws UsernameAlreadyExistsException {

        //check if username already exists
        Optional<User> existing = repository.findByEmail(request.getEmail());
        if(existing.isPresent()){
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        //create new user
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail().toLowerCase())
                .passwd(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        repository.save(user);

        // generate JWT token
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .email(user.getEmail())
                .role(String.valueOf(user.getRole()))
                .token(jwtToken)
                .build();
    }

    /*
    this is the login service method.
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail().toLowerCase(),
                        request.getPassword()));
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(()->new UsernameNotFoundException("Invalid email"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .email(user.getEmail())
                .role(String.valueOf(user.getRole()))
                .token(jwtToken)
                .build();
    }
}

