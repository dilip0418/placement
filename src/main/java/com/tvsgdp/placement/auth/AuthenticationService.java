package com.tvsgdp.placement.auth;
import com.tvsgdp.placement.config.JwtService;
import com.tvsgdp.placement.exception.InvalidCredentialsException;
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

import java.util.Objects;
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
        var savedUser = repository.save(user);

        // generate JWT token
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .role(String.valueOf(savedUser.getRole()))
                .token(jwtToken)
                .build();
    }

    /*
    this is the login service method.
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws InvalidCredentialsException {
        // before authenticating the user validate the credentials and send suitable error message
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(()->new UsernameNotFoundException("Invalid email"));

        System.out.println(user+"In User Authentication");
        System.out.println(request.getRole()+" "+user.getRole());

        if( !Objects.equals(request.getRole(), user.getRole().name())){
            throw new InvalidCredentialsException("Invalid role: " + request.getRole());
        }
        if(!passwordEncoder.matches(request.getPassword(), user.getPasswd())){
            throw new InvalidCredentialsException("Invalid password");
        }

        //If the user is valid then start the authentication process
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail().toLowerCase(),
                        request.getPassword()));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(String.valueOf(user.getRole()))
                .token(jwtToken)
                .build();
    }
}

