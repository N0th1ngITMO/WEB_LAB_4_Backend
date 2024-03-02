package ru.kkettch.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PwUtils {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PwUtils(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String hashPassword(String pwd) {
        return passwordEncoder.encode(pwd);
    }
}
