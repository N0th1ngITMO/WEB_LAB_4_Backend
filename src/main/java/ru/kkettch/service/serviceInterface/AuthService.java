package ru.kkettch.service.serviceInterface;

import jakarta.servlet.http.HttpServletResponse;
import ru.kkettch.dataTransferObject.JWTReqDto;
import ru.kkettch.dataTransferObject.JwtRespDto;

public interface AuthService {
    JwtRespDto login(JWTReqDto jwtRequestDto, HttpServletResponse response);

    JwtRespDto register(JWTReqDto jwtRequestDto, HttpServletResponse response);

    JwtRespDto logout(HttpServletResponse response);
}
