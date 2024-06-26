package com.ftn.sbnz.service.security.jwt;

import com.ftn.sbnz.service.security.SecurityUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${token.expiration}")
    private Long expiration;

    @Value("${jwt.refreshExpiration}")
    private Long refreshExpiration;

    public String getUsername(String token){
        return getClaim(token, Claims::getSubject);
    }

    public ArrayList<HashMap<String, String>> getRole(String token){
        Map<String, Object> claims;
        claims = getAllClaims(token);
        return (ArrayList<HashMap<String, String>>) claims.get("role");
    }

    public Integer getId(String token){
        Map<String, Object> claims;
        claims = getAllClaims(token);
        return (Integer) claims.get("id");
    }
    public Date getExpirationDate(String token){
        return getClaim(token, Claims::getExpiration);
    }
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaims(String token){
        return (Claims) Jwts.parser()
                .setSigningKey(secret.getBytes(Charset.forName("UTF-8")))
                .parse(token).getBody();
    }

    public Boolean isExpired(String token){
        final Date expiration = getExpirationDate(token);
        return expiration.before(new Date());
    }

    public String generateToken(SecurityUser userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userDetails.getUsername());
        claims.put("role", userDetails.getAuthorities());
        claims.put("created", new Date());
        claims.put("id", userDetails.getId());
        return this.generateToken(claims);
    }
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + (this.expiration * 1000));
    }

    private Date generateRefreshExpirationDate(){
        return new Date(System.currentTimeMillis() + (this.refreshExpiration * 1000));
    }

    private String generateToken(Map<String, Object> claims) {

        try {
            return Jwts
                    .builder()
                    .setClaims(claims)
                    .setExpiration(this.generateExpirationDate())
                    .signWith(SignatureAlgorithm.HS512, this.secret.getBytes("UTF-8"))
                    .compact();
        } catch (UnsupportedEncodingException ex) {
            return Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(this.generateExpirationDate())
                    .signWith(SignatureAlgorithm.HS512, this.secret)
                    .compact();
        }

    }
    public String generateRefreshToken(SecurityUser userDetails){
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userDetails.getUsername());
        claims.put("role", userDetails.getAuthorities());
        claims.put("created", new Date());
        claims.put("id", userDetails.getId());
        return this.generateRefreshToken(claims);
    }

    private String generateRefreshToken(Map<String, Object> claims){
        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(this.generateRefreshExpirationDate())
                    .signWith(SignatureAlgorithm.HS512, this.secret.getBytes("UTF-8"))
                    .compact();
        }catch (UnsupportedEncodingException ex){
            return Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(this.generateRefreshExpirationDate())
                    .signWith(SignatureAlgorithm.HS512, this.secret)
                    .compact();
        }
    }



    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = getUsername(token);
        return (username.equals(userDetails.getUsername()) && !isExpired(token));
    }

}