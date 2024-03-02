package ru.kkettch.service;

import com.auth0.jwt.JWT;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import ru.kkettch.dataTransferObject.JWTReqDto;
import ru.kkettch.dataTransferObject.JwtRespDto;
import ru.kkettch.exception.Exception;
import ru.kkettch.service.serviceInterface.AuthService;
import ru.kkettch.service.serviceInterface.UserService;
import ru.kkettch.springSecurity.IJwtUtils;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final IJwtUtils ijwtUtils;
    private final static short LOGOUT_COOKIE_AGE = 1;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserService userService, IJwtUtils ijwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.ijwtUtils = ijwtUtils;
    }

    @Override
    public JwtRespDto login(JWTReqDto jwtRequestDto, HttpServletResponse response) {

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequestDto.username(), jwtRequestDto.password()));
        } catch (AuthenticationException ex) {
            throw new Exception("Login or password are incorrect", HttpStatus.BAD_REQUEST);
        }

        String token = ijwtUtils.generateAccessToken(authentication);

        Cookie cookie = new Cookie("Token", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3600);
        cookie.setPath("/");
        response.addCookie(cookie);

        return new JwtRespDto(JWT.decode(token).getExpiresAt().getTime(), "JWT");
    }

    @Override
    public JwtRespDto register(JWTReqDto jwtRequestDto, HttpServletResponse response) {

        if (userService.isUserExistByName(jwtRequestDto.username())) {
            throw new Exception("Login already exists", HttpStatus.BAD_REQUEST);
        }
        System.out.println("djn");
        userService.addUser(jwtRequestDto, "USER");
        System.out.println("5");
        JwtRespDto jwtResponseDto = login(jwtRequestDto, response);
        System.out.println("6");
        return jwtResponseDto;
    }

    @Override
    public JwtRespDto logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("Token", "");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(LOGOUT_COOKIE_AGE);
        cookie.setPath("/");
        response.addCookie(cookie);
        return new JwtRespDto(new Date().getTime(), "JWT", "You are logged out");
    }
}
