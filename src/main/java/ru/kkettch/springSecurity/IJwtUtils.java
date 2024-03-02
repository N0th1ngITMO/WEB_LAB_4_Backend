package ru.kkettch.springSecurity;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface IJwtUtils {
    String generateAccessToken(Authentication authentication);

    String generateAccessToken(String username, Collection<GrantedAuthority> roleEntities);

    Claims getClaimsFromToken(String token);

    String getUserNameFromToken(String token);

    boolean validateAccessToken(String token);

    boolean validateToken(String token);
}
