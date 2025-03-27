package com.dkelly205.task_management_system.service;

import com.dkelly205.task_management_system.dto.JwtAuthResponse;
import com.dkelly205.task_management_system.dto.LoginDto;
import com.dkelly205.task_management_system.dto.RegisterDto;

public interface AuthService {

    String register(RegisterDto registerDto);

    JwtAuthResponse login(LoginDto loginDto);
}
