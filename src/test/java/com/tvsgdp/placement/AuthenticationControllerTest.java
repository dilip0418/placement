package com.tvsgdp.placement;

import com.tvsgdp.placement.auth.*;
import com.tvsgdp.placement.exception.InvalidCredentialsException;
import com.tvsgdp.placement.exception.UsernameAlreadyExistsException;
import com.tvsgdp.placement.user.Role;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthenticationControllerTest {


    /*
    Using mockito to test the authentication method
    1. Mock the authentication service
    2. Mock the user repository
    3. Test the authentication service
     */
    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    public void testRegisterSuccessful() throws UsernameAlreadyExistsException {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName("Tushar");
        registerRequest.setEmail("tushar@gmail.com");
        registerRequest.setPassword("tushar@4321");
        registerRequest.setRole(Role.ROLE_CORPORATE);


        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setId(5L);
        authenticationResponse.setEmail("tushar@gmail.com");
        authenticationResponse.setRole(String.valueOf(Role.ROLE_CORPORATE));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String token = passwordEncoder.encode("tushar@4321");
        authenticationResponse.setToken(token);

        when(authenticationService.register(registerRequest)).thenReturn(authenticationResponse);

        // Act
        ResponseEntity<Object> responseEntity = authenticationController.register(registerRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("User registration successful", ((Map<?, ?>) Objects.requireNonNull(responseEntity.getBody())).get("message"));
        assertEquals(201, (int) ((Map<?, ?>) responseEntity.getBody()).get("status"));

        AuthenticationResponse obtainedAuthResponse = (AuthenticationResponse) ((Map<?, ?>) responseEntity.getBody()).get("data");
        assertEquals(authenticationResponse.getId(), obtainedAuthResponse.getId());
        assertEquals(authenticationResponse.getEmail(), obtainedAuthResponse.getEmail());
        assertEquals(authenticationResponse.getRole(), obtainedAuthResponse.getRole());
        assertEquals(token, obtainedAuthResponse.getToken());

        verify(authenticationService).register(registerRequest);
    }



    @Test
    public void testRegisterUsernameAlreadyExists() throws UsernameAlreadyExistsException {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName("Ganesh");
        registerRequest.setEmail("ganesh@gmail.com");
        registerRequest.setPassword("gani@4321");
        registerRequest.setRole(Role.ROLE_UNIVERSITY);

        when(authenticationService.register(registerRequest)).thenThrow(new UsernameAlreadyExistsException("Username already exists"));

        // Act
        ResponseEntity<Object> responseEntity = authenticationController.register(registerRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Username already exists", ((Map<?,?>) Objects.requireNonNull(responseEntity.getBody())).get("message"));
        assertEquals(400, (int) ((Map<?,?>) responseEntity.getBody()).get("status"));

        verify(authenticationService).register(registerRequest);
    }

    @Test
    public void testAuthenticateSuccessful() throws InvalidCredentialsException {
        // Arrange
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail("dilip@gmail.com");
        authenticationRequest.setPassword("dilip@1234");

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setId(5L);
        authenticationResponse.setEmail("dilip@gmail.com");
        authenticationResponse.setRole(String.valueOf(Role.ROLE_CORPORATE));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String token = passwordEncoder.encode("dilip@1234");
        authenticationResponse.setToken(token);

        when(authenticationService.authenticate(authenticationRequest)).thenReturn(authenticationResponse);

        // Act
        ResponseEntity<Object> responseEntity = authenticationController.authenticate(authenticationRequest);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("User authentication successful", ((Map<?,?>) Objects.requireNonNull(responseEntity.getBody())).get("message"));
    }

    @Test
    public void testAuthenticateInvalidCredentials() throws InvalidCredentialsException {
        // Arrange
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail("dilip@gmail.com");
        authenticationRequest.setPassword("<PASSWORD>");

        when(authenticationService.authenticate(authenticationRequest)).thenThrow(new InvalidCredentialsException("Invalid credentials"));

        // Act
        ResponseEntity<Object> responseEntity = authenticationController.authenticate(authenticationRequest);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals("Invalid credentials", ((Map<?,?>) Objects.requireNonNull(responseEntity.getBody())).get("message"));
    }
}
