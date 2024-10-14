package com.airbnb.service;

import com.airbnb.entity.AppUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.algorithm.key}")
    private String algorthimKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiration}")
    private int expiration;

    private Algorithm algorithm;
    private static final String USER_NAME = "username";

    @PostConstruct
    public void postConstruct() throws Exception {
        algorithm = Algorithm.HMAC256(algorthimKey);
    }

    public String generateToken(AppUser user){
        return JWT.create()
                .withClaim(USER_NAME, user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+ expiration))
                .withIssuer(issuer).sign(algorithm);
    }

    public String getUserName(String token){

        return JWT.require(algorithm)
                .withIssuer(issuer)
                .build()
                .verify(token)
                .getClaim(USER_NAME).asString();
    }

}
