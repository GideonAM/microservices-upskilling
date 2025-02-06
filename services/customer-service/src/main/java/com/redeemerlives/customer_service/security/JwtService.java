package com.redeemerlives.customer_service.security;

import com.redeemerlives.customer_service.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtService {

    private final TokenRepository tokenRepository;

    public String getUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSecretKey() {
        byte[] decoded = Decoders.BASE64.decode("ThisIsTheSecretKeyThisIsTheSecretKeyThisIsTheSecretKeyThisIsTheSecretKey");
        return Keys.hmacShaKeyFor(decoded);
    }

    public boolean isValidToken(String token) {
        return expirationDate(token).after(new Date()) &&
                tokenRepository.findByToken(token).isPresent();
    }

    private Date expirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .signWith(getSecretKey())
                .compact();
    }
}
