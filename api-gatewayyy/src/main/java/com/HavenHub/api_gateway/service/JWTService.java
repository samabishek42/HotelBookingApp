package com.HavenHub.api_gateway.service;//package com.HavenHub.api_gateway.service;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.SecretKey;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//
//@Service
//public class JWTService {
//      private String secretKey = "N2VGejNuOV5US3F2V3pUNzFNc3BLO0hOXFFGMnJYOV9zZmNEOXR1eEdkUHpIekx6WU5ab0N6N2V4YTd4bWpmbHQA";
//
//
//      public JWTService() {
//      }
//
//      public String generateToken(String username) {
//            Map<String, Object> claims = new HashMap<>();
//            return Jwts.builder().claims().add(claims).subject(username).
//                    issuedAt(new Date(System.currentTimeMillis())).
//                    expiration(new Date(System.currentTimeMillis() + 108000000L)).and().signWith(this.getKey()).compact();
//      }
//
//      private SecretKey getKey() {
//            byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
//            return Keys.hmacShaKeyFor(keyBytes);
//      }
//
//      public String extractUserName(String token) {
//            return this.extractClaim(token, Claims::getSubject);
//      }
//
//      private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
//            Claims claims = this.extractAllClaims(token);
//            return claimResolver.apply(claims);
//      }
//
//      private Claims extractAllClaims(String token) {
//            return Jwts.parser().
//                    verifyWith(this.getKey()).build().parseSignedClaims(token).getPayload();
//      }
//
//      public boolean validateTokens(String token, UserDetails userDetails) {
//            String userName = this.extractUserName(token);
//            return userName.equals(userDetails.getUsername()) && !this.isTokenExpired(token);
//      }
//
//      private boolean isTokenExpired(String token) {
//            return this.extractExpiration(token).before(new Date());
//      }
//
//      private Date extractExpiration(String token) {
//            return this.extractClaim(token, Claims::getExpiration);
//      }
//
//
//}




import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JWTService {
      private String secretKey = "";

      private final Set<String> blacklistedTokens = ConcurrentHashMap.newKeySet();

      public JWTService() {
            try {
                  KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
                  SecretKey sk = keyGen.generateKey();
                  this.secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
            } catch (NoSuchAlgorithmException var3) {
                  NoSuchAlgorithmException e = var3;
                  throw new RuntimeException(e);
            }
      }

      public String generateToken(String username) {
            Map<String, Object> claims = new HashMap<>();
            return Jwts.builder().claims().add(claims).subject(username).
                    issuedAt(new Date(System.currentTimeMillis())).
                    expiration(new Date(System.currentTimeMillis() + 1080000L)).and().signWith(this.getKey()).compact();
      }

      private SecretKey getKey() {
            byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
            return Keys.hmacShaKeyFor(keyBytes);
      }

      public String extractUserName(String token) {
            return this.extractClaim(token, Claims::getSubject);
      }

      private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
            Claims claims = this.extractAllClaims(token);
            return claimResolver.apply(claims);
      }

      private Claims extractAllClaims(String token) {
            return Jwts.parser().
                    verifyWith(this.getKey()).build().parseSignedClaims(token).getPayload();
      }

      public boolean validateTokens(String token, UserDetails userDetails) {
            if (isTokenBlacklisted(token)) {
                  throw new RuntimeException("Token has been invalidated");
            }
            String userName = this.extractUserName(token);
            return userName.equals(userDetails.getUsername()) && !this.isTokenExpired(token);
      }

      private boolean isTokenExpired(String token) {
            return this.extractExpiration(token).before(new Date());
      }

      private Date extractExpiration(String token) {
            return this.extractClaim(token, Claims::getExpiration);
      }

      public void blacklistToken(String token) {
            blacklistedTokens.add(token);
      }

      public boolean isTokenBlacklisted(String token) {
            return blacklistedTokens.contains(token);
      }
}