package com.semillerogtc.gtcusermanagament.app;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {


        @Value("${jwt.secret}")
        private String secretKey;

        @Value("${jwt.expiration}")
        private Long expiration;

        public String generateToken(Authentication authentication) {
            User user = (User) authentication.getPrincipal();
            Date now = new Date();
            Date expirationDate = new Date(now.getTime() + expiration);
            return Jwts.builder()
                    .setSubject(user.getUsername())
                    .setIssuedAt(now)
                    .setExpiration(expirationDate)
                    .signWith(SignatureAlgorithm.HS512, secretKey)
                    .compact();
        }

        public String getUsernameFromToken(String token) {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        }

        public boolean validateToken(String token) {
            try {
                Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
                return true;
            } catch (JwtException e) {
                return false;
            }
        }
    }


