package com.pragma.powerup.restaurantmicroservice.configuration.security.jwt;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtProvider {
    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    public String getNombreUsuarioFromToken(String token) {
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("token mal formado");
        } catch (UnsupportedJwtException e) {
            logger.error("token no soportado");
        } catch (ExpiredJwtException e) {
            logger.error("token expirado");
        } catch (IllegalArgumentException e) {
            logger.error("token vacío");
        } catch (SignatureException e) {
            logger.error("fail en la firma");
        }
        return false;
    }

    public JWTClaimsSet getClaims(String token) {
        try {
            return JWTParser.parse(token).getJWTClaimsSet();
        } catch (ParseException e) {
            logger.error("Error al analizar el token JWT", e);
            // Manejar el error de análisis según sea necesario
        }
        return null;
    }

    @SneakyThrows
    public List<GrantedAuthority> getAuthorities(String token) {
        JWT jwt = JWTParser.parse(token);
        JWTClaimsSet claimsSet = jwt.getJWTClaimsSet();
        if (claimsSet != null) {
            List<String> roles = (List<String>) claimsSet.getClaim("roles");
            if (roles != null) {
                return roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }

}
