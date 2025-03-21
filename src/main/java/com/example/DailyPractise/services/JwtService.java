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

//    HEADER.PAYLOAD.SIGNATURE    ------- this is how jwt is


    @Value("${jwt.secrectkey}")
    private String secrectKey;

    private SecretKey getSecrectKey(){
        return Keys.hmacShaKeyFor(secrectKey.getBytes(StandardCharsets.UTF_8));
    }

        /*
            The subject field (sub in JWT payload) typically contains the primary identifier of the user.
    Here, user.getId().toString() converts the user's ID to a String and sets it as the subject of the JWT.

            Claims are additional key-value pairs that provide extra context about the user.
    The "email" claim stores the email address of the user.
    This claim adds user roles (e.g., ADMIN, USER) to the JWT.

    The "iat" (Issued At) claim records when the JWT was generated.

    The "exp" (Expiration) claim defines when the JWT expires.
    System.currentTimeMillis() returns the current timestamp.
    + 1000 * 60 means add 60 seconds (1 minute).
    The JWT will be invalid after 1 minute.

    JWTs must be signed to ensure their authenticity.
    signWith(getSecrectKey()) digitally signs the token using a secret key.
    The secret key is used for both signing and verifying the JWT.

    .compact() converts everything into a JWT string.
A final JWT looks like this:

     */
    public String generateToken(UserEntity user){
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email",user.getEmail())
                .claim("roles", Set.of("ADMIN","USER"))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60))
                .signWith(getSecrectKey())
                .compact();
    }


    public Long getUserIdFromToken(String token){
        Claims claim = Jwts.parser()
                .verifyWith(getSecrectKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return Long.valueOf(claim.getSubject());
    }
}
