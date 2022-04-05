// reference: https://github.com/RameshMF
// unfamiliar
package com.springboot.blog.security;

import com.springboot.blog.exception.BlogAPIException;
import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;

import java.security.SignatureException;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    // token generation
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationInMs);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .compact();
        return token;
    }

    // get username from the token
    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // validate JWT token
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }
//        catch (SignatureException ex){
//            throw new BlogAPIException("Invalid JWT signature",HttpStatus.BAD_REQUEST);
//        }
        catch (MalformedJwtException ex) {
            throw new BlogAPIException("Invalid JWT token",HttpStatus.BAD_REQUEST);
        } catch (ExpiredJwtException ex) {
            throw new BlogAPIException("Expired JWT token", HttpStatus.BAD_REQUEST);
        } catch (UnsupportedJwtException ex) {
            throw new BlogAPIException("Unsupported JWT token",HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException ex) {
            throw new BlogAPIException("JWT claims string is empty.", HttpStatus.BAD_REQUEST);
        }
    }

}
