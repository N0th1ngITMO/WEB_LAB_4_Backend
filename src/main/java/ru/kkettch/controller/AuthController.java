package ru.kkettch.controller;

import jakarta.servlet.http.HttpServletResponse;
import ru.kkettch.dataTransferObject.JWTReqDto;
import ru.kkettch.dataTransferObject.JwtRespDto;
import ru.kkettch.service.serviceInterface.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtRespDto> login(@RequestBody JWTReqDto requestDto, HttpServletResponse response) {
        JwtRespDto responseDto = authService.login(requestDto, response);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<JwtRespDto> register(@RequestBody JWTReqDto requestDto, HttpServletResponse response) {
        return new ResponseEntity<>(authService.register(requestDto, response), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        return new ResponseEntity<>(authService.logout(response), HttpStatus.OK);
    }
}
