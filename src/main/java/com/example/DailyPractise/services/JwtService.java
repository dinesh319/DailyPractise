package com.example.DailyPractise.services;

import com.example.DailyPractise.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

@Service
public class JwtService {

    @Value(value = "${jwt.secretkey}")
    private String secretkey;

    public SecretKey getSecrekKey(){
        return Keys.hmacShaKeyFor(secretkey.getBytes(StandardCharsets.UTF_8));
    }


    public String generateToken(UserEntity userEntity){

        return Jwts.builder()
                .subject(userEntity.getId().toString())
                .claim("email",userEntity.getEmail())
                .claim("roles", Set.of("ADMIN" , "USER"))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60))  // kept expiration for one minute
                .signWith(getSecrekKey())
                .compact();
    }

    public  Long getUserIdFromToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getSecrekKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.valueOf(claims.getSubject());
    }

}
