package kr.co.frankit_assignment.core.user.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.UUID;
import kr.co.frankit_assignment.core.user.User;
import kr.co.frankit_assignment.core.user.UserData;
import kr.co.frankit_assignment.core.user.UserFactory;
import kr.co.frankit_assignment.core.user.service.exception.UserJwtException;
import kr.co.frankit_assignment.core.user.vo.UserRoleType;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    private final Key key;
    private final long accessTokenValidity = 1000 * 60 * 15;

    public JwtTokenProvider(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public UserJwt generateToken(UUID userId, UserDetails userDetails) {
        return UserJwt.builder()
                .accessToken(this.createAccessToken(userId, userDetails))
                .expiresIn((int) accessTokenValidity / 1000)
                .build();
    }

    public User claimsToUser(Claims claims) {
        var id = claims.get("id", String.class);
        var role = claims.get("roleType", String.class);

        return new UserFactory(
                        UserData.builder()
                                .id(UUID.fromString(id))
                                .email(claims.get("email", String.class))
                                .roleType(UserRoleType.findBy(role))
                                .build())
                .create();
    }

    public Claims validateAndParse(String token) {
        try {
            return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        } catch (JwtException e) {
            throw new UserJwtException(e.getMessage(), e);
        }
    }

    private String createAccessToken(@NonNull UUID userId, @NonNull UserDetails userDetails) {
        var now = new Date();
        var validity = new Date(now.getTime() + accessTokenValidity);

        return Jwts.builder()
                .setSubject(userId.toString())
                .setClaims(this.createClaims(userDetails))
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }

    private Claims createClaims(@NonNull UserDetails userDetails) {
        var claims = Jwts.claims();
        var user = (User) userDetails;

        claims.put("id", user.getId());
        claims.put("email", user.getUsername());
        claims.put("roleType", user.getRoleType().toString());

        return claims;
    }
}
