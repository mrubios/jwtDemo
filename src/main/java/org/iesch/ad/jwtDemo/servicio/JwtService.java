package org.iesch.ad.jwtDemo.servicio;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class JwtService {
    static String secret = "Mucho texto... BLA BLA BLA BLA BLA BLA";
    static Key hmacKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
    public String creaJWT() {
        Instant now = Instant.now();
        String jwtToken = Jwts.builder().claim("name", "Michel")
                .claim("email", "mrubios@iesch.org")
                .setSubject("Miguel Ángel")
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(5l, ChronoUnit.HOURS)))
                .signWith(hmacKey)
                .compact();

        return  jwtToken;
    }

    public Jws<Claims> parseJwt(String jwtString) {
        Jws<Claims> jwt = Jwts.parserBuilder().setSigningKey(hmacKey).build().parseClaimsJws(jwtString);
        log.info(jwt.toString());
        return jwt;
    }
}
