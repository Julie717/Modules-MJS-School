package com.epam.esm.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
@Log4j2
public class JwtProvider {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.availableDays}")
    private int availableDays;

    public String generateToken(String login) {
        Date date = Date.from(LocalDate.now().plusDays(availableDays).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        boolean isValid=false;
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            isValid= true;
        } catch (ExpiredJwtException ex) {
            log.log(Level.ERROR, "Token expired");
        } catch (UnsupportedJwtException ex) {
            log.log(Level.ERROR, "Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            log.log(Level.ERROR, "Malformed jwt");
        } catch (SignatureException sEx) {
            log.log(Level.ERROR, "Invalid signature");
        } catch (Exception e) {
            log.log(Level.ERROR, "invalid token");
        }
        return isValid;
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}