package com.dkelly205.task_management_system.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderImpl {

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        System.out.println("user " + passwordEncoder.encode("password"));
        System.out.println("admin " + passwordEncoder.encode("admin"));
    }
}
