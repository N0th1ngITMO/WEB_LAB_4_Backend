package ru.kkettch.springSecurity.jwtImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import ru.kkettch.service.UserServiceImpl;
import ru.kkettch.springSecurity.IJwtUtils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;

@Component
@Slf4j
public class JwtUtils implements IJwtUtils {
    private String secretKey = Arrays.toString(Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded());;
    private Duration tokenLifeTime = Duration.ofMinutes(60);
    private UserServiceImpl userService;


    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    @Override
    public String generateAccessToken(Authentication authentication) {

        Date start = new Date();
        Date end = new Date(start.getTime() + tokenLifeTime.toMillis());

        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(start).setExpiration(end)
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();

    }

    @Override
    public String generateAccessToken(String username, Collection<GrantedAuthority> roleEntities) {
        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = roleEntities.stream().map(GrantedAuthority::getAuthority).toList();
        claims.put("roles", rolesList);
        Date start = new Date();
        Date end = new Date(start.getTime() - tokenLifeTime.toMillis());

        return Jwts.builder().setClaims(claims)
                .setSubject(username).setIssuedAt(start)
                .setExpiration(end).signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    @Override
    public Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    @Override
    public String getUserNameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    @Override
    public boolean validateAccessToken(String token) {
        return validateToken(token);
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException ex) {
            return false;
        }
    }
}