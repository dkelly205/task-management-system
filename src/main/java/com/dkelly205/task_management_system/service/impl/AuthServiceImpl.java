package com.dkelly205.task_management_system.service.impl;

import com.dkelly205.task_management_system.dto.JwtAuthResponse;
import com.dkelly205.task_management_system.dto.LoginDto;
import com.dkelly205.task_management_system.dto.RegisterDto;
import com.dkelly205.task_management_system.entity.Role;
import com.dkelly205.task_management_system.entity.User;
import com.dkelly205.task_management_system.exceptions.TaskApiException;
import com.dkelly205.task_management_system.repository.RoleRepository;
import com.dkelly205.task_management_system.repository.UserRepository;
import com.dkelly205.task_management_system.security.JwtTokenProvider;
import com.dkelly205.task_management_system.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public String register(RegisterDto registerDto) {

        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new TaskApiException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new TaskApiException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        User user = User.builder()
                .email(registerDto.getEmail())
                .username(registerDto.getUsername())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .build();

        Role role = roleRepository.findByName("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);

        return "User successfully registered";
    }

    @Override
    public JwtAuthResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtTokenProvider.generateToken(authentication);

        Optional<User> optionalUser = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail());


        String role = getRoleName(optionalUser);

        return JwtAuthResponse.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .role(role)
                .build();

    }

    private static String getRoleName(Optional<User> optionalUser){
        String roleName = null;
        if(optionalUser.isPresent()){

            User user = optionalUser.get();

            Optional<Role> optionalRole = user.getRoles().stream().findFirst();

            if(optionalRole.isPresent()){
                roleName = optionalRole.get().getName();
            }

        }

        return roleName;
    }
}
