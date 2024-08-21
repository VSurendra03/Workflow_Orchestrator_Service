package org.carlisting.workfloworchestrator.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

//    @Value("${jwt.secret}")
//    private String jwtSecret;

//    @Value("${jwt.expiration}")
//    private long jwtExpiration;

    public String generateToken() {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 86400000);

        return Jwts.builder()
                .setSubject("service-to-service")
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .compact();
    }
}
