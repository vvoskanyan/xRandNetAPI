package com.ysu.xrandnet.controllers;

import com.ysu.xrandnet.exceptions.AppException;
import com.ysu.xrandnet.models.Role;
import com.ysu.xrandnet.models.RoleName;
import com.ysu.xrandnet.models.User;
import com.ysu.xrandnet.payloads.ApiResponse;
import com.ysu.xrandnet.payloads.JWTAuthenticationResponse;
import com.ysu.xrandnet.payloads.LoginRequest;
import com.ysu.xrandnet.payloads.SignUpRequest;
import com.ysu.xrandnet.repos.RoleRepository;
import com.ysu.xrandnet.repos.UserRepository;
import com.ysu.xrandnet.security.JWTTokenProvider;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final
    AuthenticationManager authenticationManager;

    private final
    UserRepository userRepository;

    private final
    RoleRepository roleRepository;

    private final
    PasswordEncoder passwordEncoder;

    private final
    JWTTokenProvider tokenProvider;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public String authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        JWTAuthenticationResponse jwtResponse = new JWTAuthenticationResponse(jwt);
        Optional<User> user = this.userRepository.findByUsername(loginRequest.getUsername());
        JSONObject jsonObject = new JSONObject();
        if (user.isPresent()) {
            try {
                jsonObject.put("id", user.get().getId());
                jsonObject.put("username", user.get().getUsername());
                jsonObject.put("firstName", user.get().getFirstName());
                jsonObject.put("lastName", user.get().getLastName());
                jsonObject.put("token", jwtResponse.getAccessToken());
                jsonObject.put("tokenType", jwtResponse.getTokenType());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject.toString();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getUsername(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}