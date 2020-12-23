package ru.geekbrains.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderGenerator {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public String StringToHash(String password) {
            return passwordEncoder.encode(password);
    }
}
