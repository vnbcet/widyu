package com.widyu.jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtGenerator {

	public String generateJwt(String userName, JwtProperties jwtproperties) {
		Claims claim = Jwts.claims().setSubject(userName);
		Date now = new Date();
		Date validity = new Date(now.getTime() + jwtproperties.getValidity());
		return Jwts.builder().setClaims(claim).setExpiration(validity)
				.signWith(SignatureAlgorithm.HS512, jwtproperties.getSecretKey()).compact();
	}

}
