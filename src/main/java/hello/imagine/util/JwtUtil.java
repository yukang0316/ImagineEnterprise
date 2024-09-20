package hello.imagine.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt_secret}")  // 설정 파일에서 비밀키 불러오기
    private String secret;
    private final long expirationTime = 1000 * 60 * 60; // 1시간

    private Key getSigningKey() {
        byte[] keyBytes = secret.getBytes();
        if (keyBytes.length < 64) {  // 64바이트 = 512비트, HS512 알고리즘에 필요한 최소 키 길이
            throw new IllegalArgumentException("The secret key must be at least 64 bytes long");
        }
        return Keys.hmacShaKeyFor(keyBytes); // 비밀키를 사용하여 안전한 키 생성
    }

    // JWT 토큰 생성
    public String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    // 토큰에서 사용자 ID 추출
    public String extractUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}