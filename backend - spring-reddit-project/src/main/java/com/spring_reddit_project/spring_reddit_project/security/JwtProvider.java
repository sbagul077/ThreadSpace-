package com.spring_reddit_project.spring_reddit_project.security;

import com.spring_reddit_project.spring_reddit_project.exceptions.SpringRedditException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.security.*;
import java.time.Instant;
import java.util.Date;

import static io.jsonwebtoken.Jwts.parser;

@Service
public class JwtProvider {

//    private final JwtEncoder jwtEncoder;
//    @Getter
    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    private KeyStore keyStore;

//    @PostConstruct
//    public void init(){
//        try{
//            keyStore = KeyStore.getInstance("JKS");
//            InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
//            keyStore.load(resourceAsStream, "secret".toCharArray());
//        }
//        catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e){
//            throw new SpringRedditException("Exception Occurred while loading keystore");
//        }
//    }
    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("PKCS12");
            InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.p12");
            if (resourceAsStream == null) {
                throw new RuntimeException("Keystore not found in classpath");
            }
            keyStore.load(resourceAsStream, "secret".toCharArray());
        } catch (Exception e) {
            throw new SpringRedditException(
                    "Exception occurred while loading keystore",
                    e
            );
        }
    }

    public String generateToken(Authentication authentication) {
        org.springframework.security.core.userdetails.User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

    public String generateTokenWithUserName(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

//    public String generateTokenWithUserName(String username) {
//        JwtClaimsSet claims = JwtClaimsSet.builder()
//                .issuer("self")
//                .issuedAt(Instant.now())
//                .expiresAt(Instant.now().plusMillis(jwtExpirationInMillis))
//                .subject(username)
//                .claim("scope", "ROLE_USER")
//                .build();
//
//        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
//    }

    private PrivateKey getPrivateKey(){
        try{
            return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
        }
        catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e){
            throw new SpringRedditException("Exception occurred while retrieving key from keystore");
        }
    }

    public boolean validateToken(String jwt){
        parser()
                .verifyWith(getPublicKey())
                .build()
                .parseClaimsJws(jwt);
        return true;
    }

    private PublicKey getPublicKey(){
        try{
            return keyStore.getCertificate("springblog").getPublicKey();
        }catch (KeyStoreException e){
            throw new SpringRedditException("Exception occurred while retrieving public key from keystore");
        }
    }

    public String getUsernameFromJwt(String token){
        Claims claims = parser()
                .verifyWith(getPublicKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
    public long getJwtExpirationInMillis(){
        return jwtExpirationInMillis;
    }

}