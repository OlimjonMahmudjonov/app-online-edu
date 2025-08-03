package uz.online_course.project.uz_online_course_project.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {
    @Value("${auth.token.jwtSecret}")
    private String secretKey;

    @Value("${auth.token.expirationInMils}")
    private int tokenTime;

    public String encode(String username, String role) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("username", username);
        extraClaims.put("role", role);

        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenTime))
                .signWith(getSecretKey())
                .compact();
    }

    public JwtDto decode(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String username = (String) claims.get("username");
        String role = (String) claims.get("role");
        return new JwtDto(username, role);
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /*public static void main(String[] args) {
        JWTUtil jwtUtil = new JWTUtil();
        // To'g'ri base64 kaliti (32 bayt)
        jwtUtil.secretKey = "dGhpcy1pcy1hLXNlY3JldC1rZXktZm9yLWp3dC1hdXRoZW50aWNhdGlvbg==";
        jwtUtil.tokenTime = 1000 * 60 * 60; // 1 soat (millisekundlarda)

        String username = "Salim";
        String role = "ROLE_ADMIN";

        String token = jwtUtil.encode(username, role);



        JwtDto decodedToken = jwtUtil.decode(token);
        System.out.println("Dekod qilingan username: " + decodedToken.getUsername());
        System.out.println("Dekod qilingan role: " + decodedToken.getRole());
        System.out.println("token: " + token);
    }*/
}